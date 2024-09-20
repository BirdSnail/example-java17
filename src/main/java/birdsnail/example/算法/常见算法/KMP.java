package birdsnail.example.算法.常见算法;

import java.util.Arrays;

/**
 * kmp算法：<br>
 * 1.生成模式串的next数组，该数组是每个模式串index位置的最大公共前后缀的长度 <br>
 * 有了相等前后缀可以保证在index位置从后前向找等同于从前向后找 <br>
 * 2.主串指针在匹配失败后不用回溯，模式串指针回到next数组最大公共前后缀相应位置<br>
 * 3.被跳过的部分不会产生漏结<br>
 */
public class KMP {


    /**
     * 返回字符串s1在s2中的位置，如果s1不包含s2，返回-1
     *
     * @param s1 主串
     * @param s2 模式串
     * @return 匹配成功的起始索引位置
     */
    public static int indexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() > s1.length()) {
            return -1;
        }

        char[] s1Arr = s1.toCharArray();
        char[] s2Arr = s2.toCharArray();
        int[] next = getNextArray(s2);
        int x = 0;
        int y = 0;
        while (x < s1Arr.length && y < s2Arr.length) {
            if (s1Arr[x] == s2Arr[y]) { // 匹配到相同字符，继续比较下一个字符
                x++;
                y++;
            } else if (y == 0) { // 没有公共前后缀
                x++;
            } else {
                // x位置不变，y位置回到最大公共前后缀位置，下次比较时可以跳过公共前后缀的比较
                y = next[y];
            }
        }
        // y超过了s2长度，代表s2全部遍历完成，匹配成功
        return y == s2.length() ? x - y : -1;
    }

    /**
     * 字符串每个位置的最大公共前后缀长度，不包含自身
     */
    private static int[] getNextArray(String s2) {
        if (s2.length() == 1) {
            return new int[]{-1};
        }
        char[] chars = s2.toCharArray();
        int[] next = new int[s2.length()];
        next[0] = -1;
        next[1] = 0;

        int i = 1;
        int cn = 0;
        while (i < s2.length() -1) {
            if (chars[cn] == chars[i]) { // 字符相等，指针都向前移动
                i++;
                next[i] = ++cn;
            } else if (next[cn] == -1) { // 没有相等字符
                next[i + 1] = 0;
                i++;
            } else {
                // 回到之前的公共前后缀相应位置，下次比较时只需要比较公共前后缀后面的一个字符
                cn = next[cn];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String s1 = "eiabcaabee";
        String s2 = "aab";
        System.out.println(Arrays.toString(getNextArray(s2)));
        System.out.println(indexOf(s1, s2));
    }

}
