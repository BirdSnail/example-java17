package birdsnail.example.算法.动态规划;

/**
 * 数字组成的字符串映射到字符：
 * 1 --> A, 2-->N, 3-->C .......26-->Z
 * 给定一串数字，问可以映射成多少种不同的字符串？
 * <p>
 * 111 -->既可以映射成'AAA',也可以映射成'AK'或者'KA',
 * 但是305不能映射成任何的字符串，因为0数字不能映射成任何字符，所以结果是0
 * </p>
 */
public class IntStringMap {


    public static int solution1(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        }
        return process1(str.toCharArray(), 0);
    }

    /**
     * 暴力递归，每个位置考虑两种情况：<br/>
     * 1. 只转化当前位置的数字为字符<br/>
     * 2. 取当前位置和下一个位置作为一个数字进行转换<br/>
     *
     * @param chars    数字字符数组
     * @param curIndex 当前来到的下标位置
     * @return 可以转换成多少种字符
     */
    private static int process1(char[] chars, int curIndex) {
        if (curIndex >= chars.length) {
            return 1; // 走到这里意味着前面的逻辑没有出错，成功找到了一种转化方法
        }
        if (chars[curIndex] == '0') {
            return 0;
        }

        // 可能性1：只转化当前数字
        int r1 = process1(chars, curIndex + 1);
        int r2 = 0;
        // 可能性2：两个数字转化为一个字符，需要满足以下条件
        if (curIndex + 1 < chars.length && chars[curIndex] < '3' && chars[curIndex + 1] < '7') {
            r2 = process1(chars, curIndex + 2);
        }

        return r1 + r2;
    }

    /**
     * 动态规划版本，相同的子问题的解放入动态规划表
     */
    public static int solution2(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        }

        char[] chars = str.toCharArray();
        int[] dp = new int[chars.length + 1];
        dp[chars.length] = 1;
        for (int index = chars.length - 1; index >= 0; index--) {
            if (chars[index] == '0') {
                continue;
            }

            // 可能性1：只转化当前数字
            int r1 = dp[index + 1];
            int r2 = 0;
            // 可能性2：两个数字转化为一个字符，需要满足以下条件
            if (index + 1 < chars.length && chars[index] < '3' && chars[index + 1] < '7') {
                r2 = dp[index + 2];
            }
            dp[index] = r1 + r2;
        }

        return dp[0];
    }


    public static void main(String[] args) {
        String str = "1532041421";
        // System.out.println((char) ('A' + 10));
        System.out.println(solution1(str));
        System.out.println(solution1("111"));

        System.out.println(solution2(str));
        System.out.println(solution2("111"));
    }
}
