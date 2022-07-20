package birdsnail.example.算法.动态规划;

/**
 * 在一个10*9大小的象棋盘里，棋子从(0,0)点出发，棋子只能走“日”，可以走k步，问k步后刚好到达目标点(a,b)有多少种走法？
 */
public class ChessGame {

    /**
     * 解法1：暴力递归，穷举出所有的可能走法，对于每一步，只有八种走法，当走完最后一步时，判断是否到达终点，到达了就意味着找到了一种走法
     *
     * @param a    目标点横坐标
     * @param b    目标点纵坐标
     * @param rest 剩余的步数
     * @return 可能的走法
     */
    public static int jump1(int a, int b, int rest) {
        return process1(a, b, rest, 0, 0);
    }

    /**
     * 递归求解，列举所属情况。
     *
     * @param a    目标点横坐标
     * @param b    目标点纵坐标
     * @param rest 剩余的步数
     * @param x    当前位置横坐标x
     * @param y    当前位置纵坐标y
     * @return 可能的走法
     */
    private static int process1(int a, int b, int rest, int x, int y) {
        // 越界情况
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        // 走完了最后一步
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }

        int ways = process1(a, b, rest - 1, x + 1, y + 2);
        ways += process1(a, b, rest - 1, x + 2, y + 1);
        ways += process1(a, b, rest - 1, x + 2, y - 1);
        ways += process1(a, b, rest - 1, x + 1, y - 2);
        ways += process1(a, b, rest - 1, x - 1, y + 2);
        ways += process1(a, b, rest - 1, x - 1, y - 2);
        ways += process1(a, b, rest - 1, x - 2, y + 1);
        ways += process1(a, b, rest - 1, x - 2, y - 1);

        return ways;
    }

    /**
     * 解法2：因为有重复的子问题，可以使用动态规划版本。有三个可变参数(x, y, rest),可以使用一个三维坐标体系。当前rest层的数据只依赖下一层。
     * 因为可以从rest为0层时开始填充
     */
    public static int jump2(int a, int b, int rest) {
        int[][][] dp = new int[10][9][rest + 1];

        dp[a][b][0] = 1;
        for (int k = 1; k < rest + 1; k++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, k - 1, x + 1, y + 2);
                    ways += pick(dp, k - 1, x + 2, y + 1);
                    ways += pick(dp, k - 1, x + 2, y - 1);
                    ways += pick(dp, k - 1, x + 1, y - 2);
                    ways += pick(dp, k - 1, x - 1, y + 2);
                    ways += pick(dp, k - 1, x - 1, y - 2);
                    ways += pick(dp, k - 1, x - 2, y + 1);
                    ways += pick(dp, k - 1, x - 2, y - 1);

                    dp[x][y][k] = ways;
                }
            }
        }

        return dp[0][0][rest];
    }


    private static int pick(int[][][] dp, int rest, int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }

    public static void main(String[] args) {
        System.out.println(jump1(1, 2, 3));
        System.out.println(jump2(1, 2, 3));
    }

}
