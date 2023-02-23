package birdsnail.example.算法.动态规划;

import java.util.*;

import birdsnail.example.entity.ProjectInfo;
import birdsnail.example.uitl.ParamUtil;

/**
 * 贪心算法
 */
public class GreedyOperation {

    /**
     * 在给定的时间范围内，安排最多次的会议，同一时间无法安排两场及以上的会议
     *
     * @implNote 每一步都在余下的会议中安排最早结束的会议，局部最优解就是全局最优解
     */
    public static List<MeetingInfo> mostMeeting(List<MeetingInfo> srcMeeting) {
        if (srcMeeting == null) {
            throw new IllegalArgumentException("参数为null");
        }

        long timeline = Long.MIN_VALUE;
        List<MeetingInfo> result = new ArrayList<>();

        srcMeeting.sort(Comparator.comparingLong(MeetingInfo::getEndTime));
        for (MeetingInfo meetingInfo : srcMeeting) {
            if (meetingInfo.getStartTime() >= timeline) {
                result.add(meetingInfo);
                timeline = meetingInfo.getEndTime();
            }
        }
        return result;
    }

    /**
     * 一条街道上有墙和住户，墙上不可以放灯，住户可以放灯也可以不放。每盏灯只能照亮它前后的位置，求使用最少的灯照亮整条街道上的住户。<br>
     *
     * @param srcStreet "X": 代表墙，"_": 代表住户。[x,x,_,x,_,x,x,_,_,x]
     */
    public static List<Integer> leastLight(List<String> srcStreet) {
        ParamUtil.checkNotNull(srcStreet);
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < srcStreet.size(); i++) {
            if (isWall(srcStreet.get(i))) {
                i++;
            } else {// 当前元素不是墙
                if (i + 1 >= srcStreet.size() || isWall(srcStreet.get(i + 1))) { // 后面是墙或者到达边界，此处住户必须放灯
                    result.add(i);
                    i++;
                } else { // 后面还是住户的话灯必须放在后一个住户上，因此不用考虑后面第二个元素是什么，因为灯一定可以覆盖到它
                    result.add(i + 1);
                    i += 2;
                }
            }
        }

        return result;
    }


    private static boolean isWall(String item) {
        return Objects.equals("X", item);
    }


    /**
     * 一个项目有花费，有利润。现有本金w可于投资项目，最大投资次数为k次，每个项目可以投资一次,并且投资是串行的。
     * 求最大的投资收益
     *
     * @param projects 项目列表
     * @param k        投资次数限制
     * @param amount   本金
     * @return k次投资的最大收益
     */
    public static int findMaxMoney(List<ProjectInfo> projects, int k, int amount) {
        PriorityQueue<ProjectInfo> minCostHeap = new PriorityQueue<>(ProjectInfo.minHeapHelperComparator);
        minCostHeap.addAll(projects); // 小根堆保存项目花费
        PriorityQueue<ProjectInfo> maxProfitHeap = new PriorityQueue<>(ProjectInfo.maxHeapHelperComparator);

        for (int i = 0; i < k; i++) {
            while (!minCostHeap.isEmpty() && minCostHeap.peek().getCost() <= amount) {
                maxProfitHeap.offer(minCostHeap.poll()); // 能够投资的项目放入大根堆
            }
            if (maxProfitHeap.isEmpty()) { // 找不到能够投资的项目了
                return amount;
            }
            amount = amount + maxProfitHeap.poll().getProfit(); // 从大根堆中获取利润最大的项目
        }

        return amount;
    }


}
