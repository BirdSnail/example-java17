package birdsnail.example.算法.动态规划;

import java.util.IllformedLocaleException;

/**
 * 背包最大价值：有若干货物，每个货物存在重量w和价值v，在不超过背包容量限制的情况下，怎么装货物可以使总价值最大？
 * ps: 可能存在一些货物w为0的情况
 */
public class PageMaxValue {

    /**
     * 暴力递归，每个货物存在2中情况，选或者不选,遍历所有的可能结果，找出最大值
     *
     * @param w    货物重量数组，index为货物编号
     * @param v    货物价值数组，index为货物编号
     * @param page 背包容量
     * @return 最大的价值
     */
    public static int maxValue1(int[] w, int[] v, int page) {
        if (page < 0) {
            return 0;
        }
        if (w.length != v.length) {
            throw new IllformedLocaleException("参数错误");
        }

        return process1(w, v, 0, page);
    }

    /**
     * 递归遍历
     *
     * @param w     货物重量数组
     * @param v     货物价值数组
     * @param index 当前货物编号
     * @param reset 背包剩余容量
     * @return 在当前状况下背包能够转下的最大价值
     */
    private static int process1(int[] w, int[] v, int index, int reset) {
        if (index >= w.length) {
            return 0;
        }
        if (reset < 0) {// 根据题意存在重量为0的货物，因此要小于0. 如果不存在重量为0的货物，小于或等于0
            return 0;
        }

        // 选择了当前货物
        int temp = reset - w[index];
        int r1 = 0;
        if (temp >= 0) {// 选择了当前货物时要判断一下背包剩余容量够不够装下
            r1 = v[index] + process1(w, v, index + 1, temp);
        }
        // 没有选择当前货物
        int r2 = process1(w, v, index + 1, reset);

        return Math.max(r1, r2);
    }


    /**
     * 动态规划版本,构建动态规划表.
     * <p>
     * 从暴力递归中我们知道，方法返回结果只与index和reset有关系，从不同的递归入口可能进入到相同的重复子问题。
     * </p>
     */
    public static int maxValue2(int[] w, int[] v, int page) {
        if (page < 0) {
            return 0;
        }
        int length = w.length;
        if (length != v.length) {
            throw new IllformedLocaleException("参数错误");
        }

        int[][] dp = new int[length + 1][page + 1];
        for (int index = length - 1; index >= 0; index--) {
            for (int reset = 0; reset <= page; reset++) {
                int temp = reset - w[index];
                int r1 = 0;
                if (temp >= 0) {// 选择了当前货物时要判断一下背包剩余容量够不够装下
                    r1 = v[index] + dp[index + 1][temp];
                }
                int r2 = dp[index + 1][reset];
                dp[index][reset] = Math.max(r1, r2);
            }
        }

        return dp[0][page];
    }

    public static void main(String[] args) {
        int[] w = {10, 3, 5, 12, 7, 20};
        int[] v = {10, 2, 12, 25, 21, 30};
        int page = 40;

        System.out.println(maxValue1(w, v, page));
        System.out.println(maxValue2(w, v, page));
    }


}
