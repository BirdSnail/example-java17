package birdsnail.example.算法.动态规划;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * 咖啡杯最少干净时间：
 * <p/>
 * 一个数组arr[],arr[i]表示第i号咖啡机生产咖啡所需要的时间，每台咖啡机生产咖啡不能并行，不同的咖啡机之间可以并行。
 * 有一台洗咖啡杯的机器，洗干净一个杯子需要的时间为a。现在有N个人去接咖啡，接完以后立马喝掉，不会花费任何时间，
 * 喝完以后可以选择去机器洗咖啡杯（洗咖啡杯的机器不能并行，洗完当前杯子后才能洗下一个），也可以选择等待b时间，咖啡杯自然挥发干净，
 * 问所有人喝完咖啡后所有的咖啡杯都变干净的最少时间？
 */
public class CaffeMachine {


    /**
     * 思路：
     * <ol>
     * <li/>制定出一个排队策略，使每个人都喝到咖啡所需要等待的总时间数最少
     * <li/>有了这个最优策略后，考虑每个人喝完咖啡后是等待自然挥发还是使用机器洗杯子，使用暴力递归遍历所有情况，进一步优化成动态规划
     * </ol>
     *
     * @param caffeMachine 代表若干咖啡机，索引为咖啡机编号，值为生成咖啡需要的时间
     * @param N            N个人
     * @param wash         机器洗干净杯子需要的时间
     * @param free         杯子自然挥发干净需要的时间
     * @return N个人喝完咖啡后杯子都变干净的最少时间
     */
    public static int bestCleanTime(int[] caffeMachine, int N, int wash, int free) {
        int[] drinks = bestDrinkTime(caffeMachine, N);
        return cupClean(drinks, 0, 0, wash, free);
    }

    /**
     * 清洗第index个人到最后一个人所有的咖啡杯所需花费的最少时间
     *
     * @param drinks     每个人喝完咖啡的时间
     * @param index      当前轮到第index个人做选择
     * @param activeLine 清洗机器可用的时间点
     * @param wash       机器清洗干净花费的时间
     * @param free       自然挥发干净需要的时间
     * @return 花费的最少时间
     */
    public static int cupClean(int[] drinks, int index, int activeLine, int wash, int free) {
        if (index == drinks.length) {
            return 0; // 没有杯子需要清洗，花费时间为0
        }

        // 选择一：使用机器清洗
        int washLine = Math.max(drinks[index], activeLine) + wash;// 机器可用并且达到了获得咖啡的时间点，取两者中的最大值
        int cleanTime1 = cupClean(drinks, index + 1, washLine, wash, free);// 清洗完剩余人杯子花费的最少时间
        int r1 = Math.max(washLine, cleanTime1); // 自己的杯子清洗完和其他人的杯子都变干净，所以要取花费时间最大值

        // 选择二：等待自然挥发
        int freeTime = drinks[index] + free;
        int cleanTime2 = cupClean(drinks, index + 1, activeLine, wash, free);
        int r2 = Math.max(freeTime, cleanTime2);// 自己的杯子挥发干净和其他人的杯子都变干净，所以要取花费时间最大值

        // 两者情况选择花费时间较少的
        return Math.min(r1, r2);
    }


    /**
     * 动态规划版本: 收两个参数影响，当前是第几个人：index，洗杯机器可用时间点：activeLine <br/>
     * activeLine的最大值是所有人都选择使用洗杯机器
     *
     * @param drinks 每个人喝完咖啡的时间
     * @param wash   机器清洗干净花费的时间
     * @param free   自然挥发干净需要的时间
     * @return 花费的最少时间
     */
    public static int cupCleanDP(int[] drinks, int wash, int free) {
        int maxActiveTime = 0;
        //
        for (int drinkTime : drinks) {
            maxActiveTime = Math.max(drinkTime, maxActiveTime) + wash;
        }

        int[][] dp = new int[drinks.length + 1][maxActiveTime + 1];
        // dp[drink.length][...] == 0 最后一层都为0

        for (int index = drinks.length -1; index >= 0; index--) {
            for (int time = 0; time <= maxActiveTime; time++) {
                int washLine = Math.max(drinks[index], time) + wash;// 机器可用并且达到了获得咖啡的时间点，取两者中的最大值

                // 这是一个无效的解，不用考虑。如同数组从左往右dp时不必考虑left>right的情况
                if (washLine > maxActiveTime) {
                    continue;
                }

                int cleanTime1 = dp[index + 1][washLine];
                int r1 = Math.max(washLine, cleanTime1); // 自己的杯子清洗完和其他人的杯子都变干净，所以要取花费时间最大值

                // 选择二：等待自然挥发
                int freeTime = drinks[index] + free;
                int cleanTime2 = dp[index + 1][time];
                int r2 = Math.max(freeTime, cleanTime2);// 自己的杯子挥发干净和其他人的杯子都变干净，所以要取花费时间最大值

                // 两者情况选择花费时间较少的
                dp[index][time] = Math.min(r1, r2);
            }
        }

        // 两者情况选择花费时间较少的
        return dp[0][0];
    }

    /**
     * 使用贪心算法，让每个人去可以最快产出咖啡的机器排队<br/>
     * 将所有的咖啡机放入一个小根堆，按照咖啡机下次可用时间排序（下次可用时间=本次可用时间 + 工作时间），处于堆顶的元素是可以最早产出咖啡的机器。当这个咖啡被使用后，
     * 它的下次产出咖啡时间就是下次被选择的时间加上生成咖啡的时间
     *
     * @param caffeMachine 咖啡机。
     * @param peopleSize   人员数量
     * @return 每个人获得咖啡的时间点，下标是人员编号
     */
    public static int[] bestDrinkTime(int[] caffeMachine, int peopleSize) {
        PriorityQueue<CaffeMachineTime> minHeap = new PriorityQueue<>(Comparator.comparingInt(CaffeMachineTime::getNextActiveTime));
        for (int time : caffeMachine) {
            minHeap.add(new CaffeMachineTime(0, time));
        }

        int[] drinks = new int[peopleSize];
        for (int i = 0; i < peopleSize; i++) {
            CaffeMachineTime ans = minHeap.poll();
            drinks[i] = Objects.requireNonNull(ans).getNextActiveTime();
            minHeap.add(new CaffeMachineTime(ans.getNextActiveTime(), ans.getWorkTime()));
        }

        return drinks;
    }


    /**
     * 封装咖啡机下次可用时间，生产咖啡耗时
     */
    static class CaffeMachineTime {

        private Integer activeTime;
        private Integer workTime;

        public CaffeMachineTime(Integer activeTime, Integer workTime) {
            this.activeTime = activeTime;
            this.workTime = workTime;
        }

        public int getActiveTime() {
            return activeTime;
        }

        public int getWorkTime() {
            return workTime;
        }

        public int getNextActiveTime() {
            return activeTime + workTime;
        }

    }


}
