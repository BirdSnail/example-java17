package birdsnail.example.entity;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目信息
 */
@Getter
@AllArgsConstructor
public class ProjectInfo {
    /**
     * 此比较器用于帮助创建一个小根堆
     */
    public static final Comparator<ProjectInfo> minHeapHelperComparator = new MinHeapHelperComparator();

    /**
     * 此比较器用于帮助创建一个大根堆
     */
    public static final Comparator<ProjectInfo> maxHeapHelperComparator = new MaxHeapHelperComparator();

    /**
     * 花费
     */
    private int cost;

    /**
     * 利润
     */
    private int profit;

    static class MinHeapHelperComparator implements Comparator<ProjectInfo> {

        @Override
        public int compare(ProjectInfo o1, ProjectInfo o2) {
            return Integer.compare(o1.getCost(), o2.getCost());
        }
    }

    static class MaxHeapHelperComparator implements Comparator<ProjectInfo> {

        @Override
        public int compare(ProjectInfo o1, ProjectInfo o2) {
            return Integer.compare(o2.getProfit(), o1.getProfit());
        }
    }

}
