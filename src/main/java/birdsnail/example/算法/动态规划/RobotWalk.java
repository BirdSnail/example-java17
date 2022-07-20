package birdsnail.example.算法.动态规划;

import java.util.HashMap;
import java.util.Map;

import birdsnail.example.entity.Point;

/**
 * @author huadong.yang
 * @date 2022-05-26 14:50
 */
public class RobotWalk {

    /**
     * 有size个格子排成一排，一个机器人要从start位置的格子移动到target位置的格子，只允许刚好走restWalk步，求有多少种走法？
     * <p>
     * 机器人walk策略:<br/>
     * 1. 当在第一个格子时只允许向右走<br/>
     * 2. 当在最后一个格子时只允许向左走<br/>
     * 3. 当在中间位置时，既可以选择向左走也可以选择向右走
     * </p>
     *
     * @param start    起点
     * @param target   终点
     * @param restWalk 允许移动的步数
     * @param size     格子的总数
     */
    public static int walkNums(int start, int target, int restWalk, int size) {
        if (start <= 0) {
            throw new IllegalArgumentException("起点不对");
        }
        return process1(start, target, restWalk, size);
    }

    public static int walkNums2(int start, int target, int restWalk, int size) {
        if (start <= 0) {
            throw new IllegalArgumentException("起点不对");
        }
        return process2(start, target, restWalk, size, new HashMap<>());
    }

    /**
     * 暴力递归。除特殊节点为，其他每个节点都有两种选择，向左或者向右, 遍历每一种情况就是在深度遍历一颗高度为restWalk的二叉树
     *
     * @param curIndex 当前位置
     * @param target   终点
     * @param restWalk 剩余的步数
     * @param size     格子的总数
     * @return 有多少种成功的走法
     */
    private static int process1(int curIndex, int target, int restWalk, int size) {
        if (restWalk == 0) { // 来到了最后一步的位置
            return curIndex == target ? 1 : 0; // 当前位置是终点，代表找到了一种走法
        }

        if (curIndex == 1) {
            return process1(2, target, restWalk - 1, size);
        }
        if (curIndex == size) {
            // 从倒数第二个点开始走
            return process1(size - 1, target, restWalk - 1, size);
        }

        // 向左走的所有走法加上向右走的所有走法
        return process1(curIndex - 1, target, restWalk - 1, size)
                + process1(curIndex + 1, target, restWalk - 1, size);
    }

    /**
     * 增加缓存，改为动态规划.因为size和target全局不变，因此当start和restWalk相同时，此方法返回结果相同
     */
    private static int process2(int curIndex, int target, int restWalk, int size, Map<Point, Integer> dp) {
        Integer res = 0;
        Point curPoint = Point.of(curIndex, restWalk);
        if ((res = dp.get(curPoint)) != null) { // 尝试从dp中获取值
            return res;
        }

        if (restWalk == 0) { // 来到了最后一步的位置
            res = curIndex == target ? 1 : 0; // 当前位置是终点，代表找到了一种走法
        } else if (curIndex == 1) {
            res = process2(2, target, restWalk - 1, size, dp);
        } else if (curIndex == size) {
            // 从倒数第二个点开始走
            res = process2(size - 1, target, restWalk - 1, size, dp);
        } else {
            // 向左走的所有走法加上向右走的所有走法
            res = process2(curIndex - 1, target, restWalk - 1, size, dp)
                    + process2(curIndex + 1, target, restWalk - 1, size, dp);
        }
        dp.put(curPoint, res);
        return res;
    }

    /**
     * 尝试了上面的递归遍历后，我们可以构建一个二维的动态规划表。dp[cur][restWalk],行代表者当前的位置，列代表着还剩余的步数。
     * 这个二维数组保存：不同位置和还剩下多少步可以走到终点两者相互组合 的所有结果
     *
     * <ol>
     *     <li/>dp[target][0]=1 --> 没有剩余的步数了，当前位置来到了target，意味着找到了一种走法<br/>
     *     <li/>dp[1][restWalk]=dp[2][restWalk-1] --> 当前位置来到了第一个位置，只能向右走，结果取决于curIndex=2， restWalk-1的结果<br/>
     *     <li/>dp[size][restWalk] = dp[size -1][restWalk-1] -->当前位置来到了末尾，只能向左走，结果取决于 curIndex= size-1, restWalk -1的结果<br/>
     *     <li/>dp[cur][restWalk] = dp[cur -1][restWalk-1] + dp[cur -1][restWalk-1] --> 向左的结果加上向右的结果
     * </ol>
     */
    private static int walkNums3(int start, int target, int restWalk, int size) {
        int[][] dp = new int[size + 1][restWalk + 1];
        dp[target][0] = 1;
        for (int i = 1; i <= restWalk; i++) {
            dp[1][i] = dp[2][i - 1];
            for (int j = 2; j < size; j++) {
                dp[j][i] = dp[j - 1][i - 1] + dp[j + 1][i - 1];
            }
            dp[size][i] = dp[size - 1][i - 1];
        }

        return dp[start][restWalk];
    }


    public static void main(String[] args) {
        System.out.println(walkNums(2, 4, 6, 5));
        System.out.println(walkNums2(2, 4, 6, 5));
        System.out.println(walkNums3(2, 4, 6, 5));
    }


}
