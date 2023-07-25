package birdsnail.example.算法.动态规划;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 */
public class 杨辉三角 {

    public static List<Integer> solution(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        int[][] cache = new int[rowIndex + 1][rowIndex + 2];
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < rowIndex + 2; j++) {
                cache[i][j] = -1;
            }
        }

        for (int i = 0; i < rowIndex + 1; i++) {
            res.add(getNum(rowIndex, i, cache));
        }
        return res;
    }

    /**
     * 获取第row行第index列位置的数据
     *
     * @param row   行
     * @param index 下标
     * @return 该位置的值
     */
    private static int getNum(int row, int index, int[][] cache) {
        if (row == 0 || row == 1) {
            return 1;
        }
        if (index == 0 || index == row) {
            return 1;
        }
        if (index > row) {
            return 0;
        }
        if (cache[row][index] >= 0) {
            return cache[row][index];
        }
        int num = getNum(row - 1, index - 1, cache) + getNum(row - 1, index, cache);
        cache[row][index] = num;
        return num;
    }

    /**
     * 动态规划版本
     */
    public static List<Integer> solution2(int rowIndex) {
        List<Integer> res = new ArrayList<>();

        // 填充dp表
        int[][] dp = new int[rowIndex + 1][rowIndex + 2];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < rowIndex + 2; j++) {
                if (i == 0 || i == 1) {
                    dp[i][j] = 1;
                } else if (j == 0 || i == j) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                }
            }
        }

        for (int i = 0; i < rowIndex + 1; i++) {
            res.add(dp[rowIndex][i]);
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println(solution(3));
        System.out.println(solution(4));

        System.out.println(solution2(3));
        System.out.println(solution2(4));
    }
}
