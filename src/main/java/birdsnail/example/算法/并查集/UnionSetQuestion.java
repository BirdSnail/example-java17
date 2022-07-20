package birdsnail.example.算法.并查集;


import birdsnail.example.uitl.CopyUtil;

/**
 * 并查集相关联系题
 */
public class UnionSetQuestion {

    private static final int RED = 2;

    /**
     * 朋友圈：
     * <p>
     * 假设有n个人，我们以一个正方形的二维数组[n][n]代表一个朋友圈，当[i][j] == 1时代表i与j是朋友互相认识，因此[j][i] == 1也成立。
     * 同时[i][i] ==1恒成立，因此这个正方性是对角线对称的，我们只需要遍历二维数组的上半部分或者下半部分
     * </p>
     */
    public static int findFriendsCircleSize(int[][] friendsRelation) {
        int m = friendsRelation.length;
        DefaultUnionSet<Integer> friends = DefaultUnionSet.ofIntRange(0, m);

        for (int i = 0; i < friendsRelation.length; i++) {
            for (int j = i + 1; j < friendsRelation.length; j++) {
                if (friendsRelation[i][j] == 1) {
                    // 是朋友就合并
                    friends.union(i, j);
                }
            }
        }

        return friends.getIsolationNums();
    }

    /**
     * 给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     *
     * @param grid 网格数据
     * @return 小岛个数
     */
    public static int numIsLands(int[][] grid) {
        int[][] gridData = CopyUtil.copyMatrix(grid);
        return solutionDyeing(gridData);
    }

    /**
     * 方案一：染色法/感染法
     * 以递归的形式将连成一片的‘1’全部‘染色’。有多少次这样的操作就代表有多少个小岛
     */
    public static int solutionDyeing(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int nums = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) { // 发现小岛，开始染色
                    nums++;
                    dyeing(i, j, grid);
                }
            }
        }
        return nums;
    }

    private static void dyeing(int i, int j, int[][] gridData) {
        if (i < 0 || i >= gridData.length || j < 0 || j >= gridData[0].length || gridData[i][j] != 1) {
            return;
        }
        // 染色，将值换成红色
        gridData[i][j] = RED;
        // 按照左右上下的顺序依次感染
        dyeing(i, j - 1, gridData);
        dyeing(i, j + 1, gridData);
        dyeing(i - 1, j, gridData);
        dyeing(i + 1, j, gridData);
    }

}
