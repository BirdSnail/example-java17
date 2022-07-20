package birdsnail.example.算法.常见算法;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字符串常见算法
 */
public class StringAlgorithm {


    /**
     * 字符串的所有子序列.<br/>
     * 每一个位置字符都有两种选择， 加入到子序列或者不加入，因此总的可能性是2的n次方。是一颗二叉决策树
     */
    public static List<String> allSubsequence(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        List<String> result = new ArrayList<>();
        process(chars, 0, result, path);
        return result;
    }

    /**
     * 一个递归过程，chars[0...index-1]已经决定好的值加上当前index位置有两种选择加入到子序列或者不加入。
     * 把所有的可能性放到ans里面
     *
     * @param chars 字符数组
     * @param index 当前下标,来到了chars[index]位置
     * @param ans   结果集，如果想要达到去重的效果可以换成set
     * @param path  chars[0...index-1]已经决定好的子序列，不能改变
     */
    private static void process(char[] chars, int index, List<String> ans, String path) {
        if (index == chars.length) {
            ans.add(path);
            return;
        }
        process(chars, index + 1, ans, path);
        String yes = path + chars[index];
        process(chars, index + 1, ans, yes);
    }

    /**
     * 字符串全排列,对于长度为n的字符串，结果集数量为n的阶乘。
     */
    public static List<String> permutation1(String str) {
        char[] chars = str.toCharArray();
        List<Character> rest = new ArrayList<>();
        for (char aChar : chars) {
            rest.add(aChar);
        }

        List<String> result = new ArrayList<>();
        String path = "";
        fp(rest, path, result);
        return result;
    }

    /**
     * 之前已经决策好的排列path，加上rest选择一个字符，每一个字符都有参与排列的可能性，收集所有可能性
     * <p>
     * 这个方法不是特别好，因为{@link List#add(int, Object)}和{@link List#remove(int)}方法都需要进行数组的拷贝和移动，增加了时间复杂度
     * </p>
     *
     * @param rest   还未参与排列的可选字符
     * @param path   前面决策好的排列
     * @param result 排列结果
     */
    private static void fp(List<Character> rest, String path, List<String> result) {
        if (rest.isEmpty()) {
            result.add(path);
            return;
        }
        int size = rest.size();
        for (int i = 0; i < size; i++) {
            Character cur = rest.get(i);
            rest.remove(i); // 清除现场，代表这个字符已经参与了排列，他的后续选择不应该包含此字符
            fp(rest, path + cur, result);
            rest.add(i, cur); // 恢复现场，下次遍历时当前字符选取别的字符
        }
    }

    /**
     * 字符串全排列,对于长度为n的字符串，结果集数量为n的阶乘。
     * <p>
     * 解决方法2: 直接在原字符数组种进行交换
     * </p>
     */
    public static List<String> permutation2(String str) {
        char[] chars = str.toCharArray();
        List<String> result = new ArrayList<>();
        gp(chars, 0, result);
        return result;
    }

    /**
     * 全排列去重
     */
    public static List<String> permutation3(String str) {
        char[] chars = str.toCharArray();
        List<String> result = new ArrayList<>();
        gp2(chars, 0, result);
        return result;
    }

    private static void gp2(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        Set<Character> visited = new HashSet<>(); // 保存与index位置交换过的字符
        for (int i = index; i < chars.length; i++) {
            if (!visited.contains(chars[i])) { // 重复的字符跳过
                visited.add(chars[i]);
                swap(chars, index, i); // 与index后面的字符都会交换一边
                gp2(chars, index + 1, result); // index交换完以后就意味着当前的排列字符已经确定，进入递归，找出index后面字符排列的可能
                swap(chars, index, i); // 恢复现场，让index位置恢复到交换之前的值,准备下一轮的交换
            }
        }
    }

    /**
     * chars[0...index-1]代表已经是交换的排列,排列结果存储在当前字符数组中。chars[index...]当前位置与后续的字符可以继续进行交换。
     * 收集每一种交换后的排列结果
     *
     * @param chars  原字符数组
     * @param index  当前位置
     * @param result 排列结果
     */
    private static void gp(char[] chars, int index, List<String> result) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i); // 与index后面的字符都会交换一边
            gp(chars, index + 1, result); // index交换完以后就意味着当前的排列字符已经确定，进入递归，找出index后面字符排列的可能
            swap(chars, index, i); // 恢复现场，让index位置恢复到交换之前的值,准备下一轮的交换
        }
    }

    private static void swap(char[] chs, int i, int j) {
        char temp = chs[i];
        chs[i] = chs[j];
        chs[j] = temp;
    }


    public static void main(String[] args) {
        System.out.println("子序列========");
        System.out.println(allSubsequence("abc"));
        System.out.println("全排列1=======");
        System.out.println(permutation1("abc"));
        System.out.println("全排列2=======");
        System.out.println(permutation2("abcd"));
        System.out.println(permutation1("abcd"));
        System.out.println(permutation3("abcc"));
    }

}
