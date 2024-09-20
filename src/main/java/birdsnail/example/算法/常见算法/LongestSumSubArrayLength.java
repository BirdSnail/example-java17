package birdsnail.example.算法.常见算法;

import java.util.HashMap;
import java.util.Map;

/**
 * 在数组所有的子数组里找到子数组之和等于k的子数组，并且子数组长度最长，返回其长度
 */
public class LongestSumSubArrayLength {


    /**
     * 规定数组的元素都是正整数。
     * <p>
     * 思路：滑动窗口
     * </p>
     *
     * @param k sum指定值
     */
    public static int findLengthInPositiveArray(int[] array, int k) {
        if (array == null || array.length == 0 || k <= 0) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int sum = array[left];
        int len = 0;
        while (left < array.length) {
            if (sum == k) { // 满足条件
                len = Math.max(len, right - left + 1);
                sum -= array[left++];
            } else if (sum < k) {
                right++;
                if (right >= array.length) {
                    break;
                }
                sum += array[right];
            } else {
                sum -= array[left++];
            }
        }

        return len;
    }

    /**
     * 规定数组中有正数，负数，0
     * <p>
     *     思路：从开头结尾处开始处理。遍历到位置i，此时整体的和为sum(0, i) = sum，如何有前缀(下标j)和为sum(0,j) =sum -k的话，意味着前缀位置到i的字串之和为k，即sum(j,i) = k
     * </p>
     */
    public static int findLengthInArray(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // key：前缀和，
        // value：前缀和对应的前缀下标，只保留最早出现的下标
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 起始位置
        int len = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            int key = sum - k;
            if (map.containsKey(key)) {
                Integer left = map.get(key);
                len = Math.max(len, i - left);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }


    /**
     * 子数组sum<=k，并且子数组长度最长，规定数组中有正数，负数，0
     */
    public static int findLessSumLengthInArray(int[] array, int k) {
        if (array == null || array.length == 0) {
            return 0;
        }
//
//        int[] minSum = ;
//        int[] minSumEnd = ;
//
        return 0;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 1, 1, 1, 4, 2, 1, 1, 1, 1};
        System.out.println(findLengthInPositiveArray(arr, 3));

        int[] arr2 = {3, -2, 1, 1, 1, 1};
        System.out.println(findLengthInArray(arr2, 3));
    }

}
