package birdsnail.example.算法.动态规划;

import java.util.HashMap;
import java.util.Map;

import birdsnail.example.entity.Point;

/**
 * @author huadong.yang
 * @date 2022-05-27 20:35
 */
public class CardWinGame {


    /**
     * 有若干张扑克牌，每张都有分数，现有两人玩游戏，每次可以拿走最左边的或者最右边的，并获得分数。一人先拿一人后拿，假定这两人都绝顶聪明，
     * 总是会从剩下的牌中选取最优解，让自己的总分数最高，求最终赢家的分数.
     * <p/>
     * 暴力递归，对于先手来说有两种情况拿左边的牌，然后进入后手阶段，从后手牌中找到选取最优解。
     *
     * @param cards 含有分数的扑克牌
     * @return 赢家的分数
     */
    public static int win1(int[] cards) {
        int fr = firstWin(cards, 0, cards.length - 1);
        int lr = lastWin(cards, 0, cards.length - 1);
        return Math.max(fr, lr);
    }

    public static int firstWin(int[] cards, int left, int right) {
        if (left == right) {
            return cards[left];
        }
        // 情况一：先手选最左的牌，然后获取剩下的牌中后手选择的最优值
        int r1 = cards[left] + lastWin(cards, left + 1, right);
        // 情况二：先手选最右的牌，然后获取剩下的牌中后手选择的最优值
        int r2 = cards[right] + lastWin(cards, left, right - 1);

        return Math.max(r1, r2);
    }

    /**
     * 后手选择等同于是从剩下的牌中先手获取自己最大的分数，表面上看对手选择了左边的牌，我从剩下的牌中选择最优的解，对手选择右边的牌同理。
     * 但是，两边都是绝顶聪明，作为先手的对手一定会选择有利于自己的那一边，留给自己的只能是收益最少的选择
     */
    private static int lastWin(int[] cards, int left, int right) {
        if (left == right) { // 只有一张牌，又是后手，先手一定会拿走这张牌使后手无法得分
            return 0;
        }

        // 情况一：对手拿走了左侧的牌，从剩下的牌中先手选择
        int r1 = firstWin(cards, left + 1, right);
        // 情况二: 对手拿走了右侧的牌，从剩下的牌中先手选择
        int r2 = firstWin(cards, left, right - 1);

        return Math.min(r1, r2);
    }

    /**
     * firstWin和lastWin结果取决于left和right。两个参数相同，方法结果就相同，因此对于重复的子问题不用进行重复的计算，可以缓存结果
     */
    public static int win2(int[] cards) {
        Map<Point, Integer> firstMap = new HashMap<>();
        Map<Point, Integer> lastMap = new HashMap<>();

        int fr = firstWin2(cards, 0, cards.length - 1, firstMap, lastMap);
        int lr = lastWin2(cards, 0, cards.length - 1, firstMap, lastMap);
        return Math.max(fr, lr);
    }

    private static int lastWin2(int[] cards, int left, int right, Map<Point, Integer> firstMap, Map<Point, Integer> lastMap) {
        Point p = Point.of(left, right);
        Integer scope = lastMap.get(p);
        if (scope != null) {
            return scope;
        }

        if (left == right) { // 只有一张牌，又是后手，先手一定会拿走这张牌使后手无法得分
            scope = 0;
        } else {
            // 情况一：对手拿走了左侧的牌，从剩下的牌中先手选择
            int r1 = firstWin2(cards, left + 1, right, firstMap, lastMap);
            // 情况二: 对手拿走了右侧的牌，从剩下的牌中先手选择
            int r2 = firstWin2(cards, left, right - 1, firstMap, lastMap);
            scope = Math.min(r1, r2);
        }

        lastMap.put(p, scope);
        return scope;
    }

    private static int firstWin2(int[] cards, int left, int right, Map<Point, Integer> firstMap, Map<Point, Integer> lastMap) {
        Point p = Point.of(left, right);
        Integer scope = firstMap.get(p);
        if (scope != null) {
            return scope;
        }

        if (left == right) {
            scope = cards[left];
        } else {
            // 情况一：先手选最左的牌，然后获取剩下的牌中后手选择的最优值
            int r1 = cards[left] + lastWin2(cards, left + 1, right, firstMap, lastMap);
            // 情况二：先手选最右的牌，然后获取剩下的牌中后手选择的最优值
            int r2 = cards[right] + lastWin2(cards, left, right - 1, firstMap, lastMap);
            scope = Math.max(r1, r2);
        }

        firstMap.put(p, scope);
        return scope;
    }


    /**
     * 动态规划终版，直接计算好动态规划表.位置依赖关系，斜下方,延对角线方向一次填充。
     */
    public static int win3(int[] cards) {
        int length = cards.length;
        int[][] firstMap = new int[length][length];
        int[][] lastMap = new int[length][length];

        for (int i = 0; i < length; i++) {
            firstMap[i][i] = cards[i];
        }

        for (int startCol = 1; startCol < length; startCol++) {
            int l = 0;
            int r = startCol;
            while (r < length) {
                // 依赖左边的位置和上边的位置
                firstMap[l][r] = Math.max(cards[l] + lastMap[l + 1][r], cards[r] + lastMap[l][r - 1]);
                lastMap[l][r] = Math.min(firstMap[l][r - 1], firstMap[l + 1][r]);
                l++;
                r++;
            }
        }

        return Math.max(firstMap[0][length - 1], lastMap[0][length - 1]);
    }


    public static void main(String[] args) {
        int[] arr = {1, 100, 1};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
        int[] arr2 = {1, 100, 1, 1};
        System.out.println(win1(arr2));
        System.out.println(win2(arr2));
        System.out.println(win3(arr2));
    }


}
