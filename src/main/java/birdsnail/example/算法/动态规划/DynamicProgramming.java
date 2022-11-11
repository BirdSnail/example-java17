package birdsnail.example.算法.动态规划;

import java.util.*;
import java.util.function.Supplier;

import birdsnail.example.entity.Point;

/**
 * 动态规划
 * - 最长公共子串
 * - 最长公共子序列
 */
public class DynamicProgramming {

    /**
     * 最长公共子串长度
     */
    public static int longestCommonSubstringLength(String first, String second) {

        int max = 0;

        int[][] table = new int[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    int value = getProcessedValue(i, j, table) + 1;
                    table[i][j] = value;
                    if (value >= max) {
                        max = value;
                    }
                } else {
                    table[i][j] = 0;
                }
            }
        }

        return max;
    }

    /**
     * 最长公共字串
     */
    public static Set<String> longestCommonSubstring(String first, String second) {

        int max = 0;
        List<Point> candidate = new ArrayList<>();

        int[][] table = new int[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    int value = getProcessedValue(i, j, table) + 1;
                    table[i][j] = value;
                    if (value >= max) {
                        max = value;
                        candidate.add(new Point(i, j));
                    }
                } else {
                    table[i][j] = 0;
                }
            }
        }

        Set<String> result = new HashSet<>();
        for (Point point : candidate) {
            int i = point.getX();
            int j = point.getY();
            if (max == table[i][j]) {
                String lcs = "";
                while (i >= 0 && j >= 0 && table[i][j] > 0) {
                    lcs = first.charAt(i) + lcs;
                    i--;
                    j--;
                }
                result.add(lcs);
            }
        }

        return result;
    }

    private static int getProcessedValue(int i, int j, int[][] table) {
        if (i < 1 || j < 1) {
            return 0;
        }
        return table[i - 1][j - 1];
    }


    /**
     * 最长公共子序列长度
     */
    public static int longestCommonSubsequenceLength(String first, String second) {
        int max = 0;
        int[][] table = new int[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    int value = getProcessedValue(i, j, table) + 1;
                    table[i][j] = value;
                    if (value >= max) {
                        max = value;
                    }
                } else {
                    table[i][j] = getMaxNeighbor(i, j, table);
                }
            }
        }

        return max;
    }

    /**
     * 最长公共子序列解法2：从暴力递归入手，然后优化成动态规划版本
     * <p/>
     * 假设字符串为X，Y，则结果可以表示为LCS(X:n,Y:m) n为X字符串的长度，m为Y的字符串长度。从字符串的最后一位开始着手，总共有三种情况：
     * <ol>
     *     <li>情况1：最后一位可能属入公共子序列的一部分，则可以将问题规模缩小为LCS(X:n-1,Y:m-1) + (X[n] == Y[m] ? 1 : 0)，变成了对原问题的子问题求解</li>
     *     <li>情况2：X的最后一位不属入公共子序列一部分，Y的可能属入，则可以将问题规模变为LCS(X:n-1,Y:m)</li>
     *     <li>情况3：X的最后一位可能属入公共子序列一部分，Y的不属入，则可以将问题规模变为LCS(X:n,Y:m-1)</li>
     * </ol>
     *
     * @param first  第一个字符串
     * @param second 第二个字符串
     * @return 最长公共子序列长度
     */
    public static int longestCommonSubsequenceLength2(String first, String second) {
        char[] firstCharArr = first.toCharArray();
        char[] secondCharArr = second.toCharArray();
        return process(firstCharArr, secondCharArr, firstCharArr.length - 1, secondCharArr.length - 1);
    }

    private static int process(char[] firstCharArr, char[] secondCharArr, int N, int M) {
        if (N == 0 && M == 0) {// 两个字符串都为单字符
            return firstCharArr[N] == secondCharArr[M] ? 1 : 0;
        }
        if (N == 0) {// 第一个字符串为单字符
            if (firstCharArr[N] == secondCharArr[M]) {
                return 1;
            } else {
                return process(firstCharArr, secondCharArr, 0, M - 1);
            }
        }
        if (M == 0) {// 第二个字符串为单字符
            if (firstCharArr[N] == secondCharArr[M]) {
                return 1;
            } else {
                return process(firstCharArr, secondCharArr, N - 1, 0);
            }
        }
        // N,M都不为0
        int r1 = process(firstCharArr, secondCharArr, N - 1, M - 1) + (firstCharArr[N] == secondCharArr[M] ? 1 : 0);// 情况1
        int r2 = process(firstCharArr, secondCharArr, N - 1, M); // 情况2
        int r3 = process(firstCharArr, secondCharArr, N, M - 1); // 情况3
        return Math.max(r1, Math.max(r2, r3)); // 取三种情况中的最大值
    }


    /**
     * 再解法2的基础上改为动态规划。再求解子问题时，一些子问题时重叠的.
     * <p/>
     * 如：LCS(X:n-1,Y:m)可以进一步变为子问题LCS(X:n-1,Y:M-1)和LCS(X:n-2,Y:m),
     * LCS(X:n-1,Y:M-1)这个子问题就变为了情况一，有重复。可以增加dp表来减少重复计算
     *
     * @param first  第一个字符串
     * @param second 第二个字符串
     * @return 最长公共子序列
     */
    public static int longestCommonSubsequenceLength3(String first, String second) {
        char[] firstCharArr = first.toCharArray();
        char[] secondCharArr = second.toCharArray();
        Map<Point, Integer> dp = new HashMap<>();
        return process3(firstCharArr, secondCharArr, firstCharArr.length - 1, secondCharArr.length - 1, dp);
    }

    private static int process3(char[] firstCharArr, char[] secondCharArr, int N, int M, Map<Point, Integer> dp) {
        Integer result = dp.get(new Point(N, M));
        if (result != null) {
            return result;
        }

        if (N == 0 && M == 0) {// 两个字符串都为单字符
            int res = firstCharArr[N] == secondCharArr[M] ? 1 : 0;
            dp.put(Point.of(0, 0), res);
            return res;
        }

        if (N == 0) {// 第一个字符串为单字符
            if (firstCharArr[N] == secondCharArr[M]) {
                dp.put(new Point(N, M), 1);
                return 1;
            } else {
                return getFromDpOrThen(dp, 0, M - 1, () -> process(firstCharArr, secondCharArr, 0, M - 1));
            }
        }
        if (M == 0) {// 第二个字符串为单字符
            if (firstCharArr[N] == secondCharArr[M]) {
                dp.put(new Point(N, M), 1);
                return 1;
            } else {
                return getFromDpOrThen(dp, N - 1, 0, () -> process(firstCharArr, secondCharArr, N - 1, 0));
            }
        }
        // N,M都不为0

        int r1 = getFromDpOrThen(dp, N - 1, M - 1,
                () -> process(firstCharArr, secondCharArr, N - 1, M - 1) + (firstCharArr[N] == secondCharArr[M] ? 1 : 0));// 情况1

        int r2 = getFromDpOrThen(dp, N - 1, M, () -> process(firstCharArr, secondCharArr, N - 1, M)); // 情况2
        int r3 = getFromDpOrThen(dp, N - 1, M, () -> process(firstCharArr, secondCharArr, N, M - 1)); // 情况3
        int fr = Math.max(r1, Math.max(r2, r3));// 取三种情况中的最大值
        dp.put(new Point(N, M), fr);
        return fr;
    }

    private static int getFromDpOrThen(Map<Point, Integer> dp, int i, int j, Supplier<Integer> action) {
        Point p = Point.of(i, j);
        Integer res = dp.get(p);
        if (res != null) {
            return res;
        }

        Integer acRes = action.get();
        dp.put(p, acRes);
        return acRes;
    }


    /**
     * 解法4：动态规划终版，再解法2，3的基础上直接填充动态规划表
     */
    public static int longestCommonSubsequenceLength4(String first, String second) {
        char[] firstCharArr = first.toCharArray();
        char[] secondCharArr = second.toCharArray();

        int[][] dp = new int[firstCharArr.length][secondCharArr.length];

        dp[0][0] = firstCharArr[0] == secondCharArr[0] ? 1 : 0;

        // 第一个字符串为单字符的情况
        for (int i = 1; i < secondCharArr.length; i++) {
            if (firstCharArr[0] == secondCharArr[i]) {
                dp[0][i] = 1;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }
        // 第二个字符串为单字符的情况
        for (int j = 1; j < firstCharArr.length; j++) {
            if (firstCharArr[j] == secondCharArr[0]) {
                dp[j][0] = 1;
            } else {
                dp[j][0] = dp[j - 1][0];
            }
        }

        // 从上倒下，从左往右
        for (int i = 1; i < firstCharArr.length; i++) {
            for (int j = 1; j < secondCharArr.length; j++) {
                int r1 = dp[i - 1][j - 1] + (firstCharArr[i] == secondCharArr[j] ? 1 : 0);// 情况1
                int r2 = dp[i - 1][j]; // 情况2
                int r3 = dp[i][j - 1]; // 情况3
                dp[i][j] = Math.max(r1, Math.max(r2, r3));// 取三种情况中的最大值
            }
        }

        return dp[firstCharArr.length - 1][secondCharArr.length - 1];
    }

    private static int getMaxNeighbor(int i, int j, int[][] table) {
        if (i == 0 && j == 0) {
            return 0;
        }
        if (i < 1 && j > 0) {
            return table[0][j - 1];
        }
        if (i > 0 && j < 1) {
            return table[i - 1][0];
        }

        return Math.max(table[i - 1][j], table[i][j - 1]);
    }

    /**
     * 最长回文子序列:一个字符串有许多子序列，可能存在一些子序列是回文，求最长的回文子序列长度
     * <p/>
     * 思路：求这个字符串和这个字符串的逆序字符串的最长公共子序列。因为回文是对称的，逆序过后相同，字符串的逆序和字符串本身的公共子序列就是回文
     *
     * @param str 属入的字符串
     * @return 字符串的最长回文子序列长度
     */
    public static int longestPalindromicSubsequenceLength(String str) {
        StringBuilder sb = new StringBuilder(str);
        String reverseStr = sb.reverse().toString();
        return longestCommonSubsequenceLength4(str, reverseStr);
    }


    /**
     * 给定一个正整数集合arr,请把arr划分为两个集合，尽量让两个子集合的的累加和接近<br/>
     * 返回：最接近情况下，较小集合的累加和
     * <p/>
     * 可以将问题变为背包问题，最理想的情况是两边相等，较小集合的累加和为sum/2,如果不相等的话较小集合的累加和小于sum/2,
     * 同时累加和要是小于sum/2中的最大值。因此问题变成了一个背包的容量为sum/2, 货物数组为arr，求背包装哪些货物能够使价值最大?
     *
     * @param arr 待拆分的数组
     */
    public static int splitArray(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        return splitArrProcess(arr, 0, sum / 2);
    }

    /**
     * 难点：转换成背包问题。数组从index开始，arr[index....]后续合适元素累加和最接近rest
     *
     * @param arr   数组
     * @param index 当前位置
     * @param rest  剩余目标数
     * @return 数组从index开始，能够得到的最接近rest的和
     */
    private static int splitArrProcess(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0; // 没有数选择了
        }
        // 情况一：没有选择当前的数
        int r1 = splitArrProcess(arr, index + 1, rest);
        int r2 = 0;
        // 情况二：选择了当前的数，只有当前数小于rest时才会有情况二
        if (rest >= arr[index]) {
            r2 = arr[index] + splitArrProcess(arr, index + 1, rest - arr[index]);
        }

        return Math.max(r1, r2);
    }

    /**
     * 拆分数组：动态规划版本
     */
    public static int splitArrayDp(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        sum = sum / 2;
        int length = arr.length;
        int[][] dp = new int[length + 1][sum + 1];

        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 情况一：没有选择当前的数
                int r1 = dp[index + 1][rest];
                // 情况二：选择了当前的数
                int r2 = 0;
                if (rest >= arr[index]) { // 不能超过rest限制
                    r2 = arr[index] + dp[index + 1][rest - arr[index]];
                }

                dp[index][rest] = Math.max(r1, r2);
            }
        }

        return dp[0][sum];
    }

    /**
     * 拆分一个数组为两个数组，如果原始数组长度偶数，则两个子数组长度相同，如果为奇数，两个子数组的长度差不超过一。
     * 满足两个子数组的累计和最为接近，求较小集合的累加和？
     *
     * @param array 数组
     * @return 较小集合的累加和
     */
    public static int minSubArraySum(int[] array) {
        int sum = Arrays.stream(array).sum() / 2;
        if (array.length % 2 == 0) {
            return subArrSumProcess(array, sum, 0, array.length / 2);
        }else {
            // 奇数时，取两种情况下数组中累加和较大的
            int a = subArrSumProcess(array, sum, 0, array.length / 2);
            int b = subArrSumProcess(array, sum, 0, array.length / 2 + 1);
            return Math.max(a, b);
        }
    }

    /**
     * arr数组从index起，选择size个数，这些数的累计和满足一个条件：值最接近rest
     *
     * @param size 还可以选择size个数
     */
    private static int subArrSumProcess(int[] array, int rest, int index, int size) {
        if (index == array.length) {
            return size == 0 ? 0 : Integer.MIN_VALUE; // 没有数可以选择了，是否达到了期望的size个数，没有达到意味返回一个代表无效的值
        }

        if (size == 0) {
            return 0;
        }

        int r1 = subArrSumProcess(array, rest, index + 1, size);
        int r2 = Integer.MIN_VALUE;
        if (rest >= array[index]) {
            int next = subArrSumProcess(array, rest - array[index], index + 1, size - 1);
            if (next != Integer.MIN_VALUE) {// 下一步的结果为有效值时才计算
                r2 = array[index] + next;
            }
        }

        return Math.max(r1, r2);
    }

    /**
     * 数组拆分二，使用动态规划优化
     * @param array
     * @return
     */
    public static int minSubArraySumDp(int[] array) {
        int sum = Arrays.stream(array).sum() / 2;

        int[][][] dp = new int[array.length + 1][sum + 1][array.length / 2 + 2];

        for (int size = 1; size <= array.length / 2 + 1; size++) {
            for (int j = 0; j <= sum; j++) {
                dp[array.length][j][size] = Integer.MIN_VALUE;
            }

            for (int index = array.length - 1; index >= 0; index--) {
                for (int rest = 0; rest <= sum; rest++) {
                    int r1 = dp[index + 1][rest][size];

                    int r2 = Integer.MIN_VALUE;
                    if (rest >= array[index]) {
                        int next = dp[index + 1][rest - array[index]][size - 1];
                        if (next != Integer.MIN_VALUE) {// 下一步的结果为有效值时才计算
                            r2 = array[index] + next;
                        }
                    }

                    dp[index][rest][size] = Math.max(r1, r2);
                }
            }
        }


        if (array.length % 2 == 0) {
            return dp[0][sum][array.length / 2];
        }else {
            // 奇数时，取两种情况下数组中累加和较大的
            int a = dp[0][sum][array.length / 2];
            int b = dp[0][sum][array.length / 2 + 1];
            return Math.max(a, b);
        }
    }

    public static void main(String[] args) {
        String first = "AeVBHC";
        String second = "AeBHC";
        System.out.println(longestCommonSubstringLength(first, second));
        System.out.println(longestCommonSubstring(first, second));
        System.out.println(longestCommonSubsequenceLength(first, second));
        System.out.println(longestCommonSubsequenceLength2(first, second));
    }


}

