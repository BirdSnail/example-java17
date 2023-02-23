package birdsnail.example.算法.数组;

import java.util.Arrays;

public class ArrayExample {

    /**
     * 一个整数数组里面元素从小到大有序排列，不适用额外空间，移除数组里面的重复元素并返回不重复元素的个数。
     */
    public static int removeDuplicates(int[] ints) {
        int k = 1;
        if (ints.length == 1) {
            return k;
        }

        int start = 0;
        int current = 1;
        while (true) {
            if (current >= ints.length) {
                break;
            }
            if (ints[current] > ints[start]) {
                start++;
                swap(ints, start, current);
                k++;
            }
            current++;
        }
        return k;
    }


    private static void swap(int[] ints, int i, int j) {
        int temp = ints[i];
        ints[i] = ints[j];
        ints[j] = temp;
    }

    /**
     * 一个整数数组里面元素从小到大有序排列，不适用额外空间，移除数组里面的重复元素并返回不重复元素的个数。
     */
    public static int removeElement(int[] ints, int element) {
        int k = 0;
        int start = 0;
        int current = 0;
        while (current < ints.length) {
            if (ints[current] != element) {
                ints[start] = ints[current];
                k++;
                start++;
            }
            current++;
        }
        return k;
    }

    /**
     * 二分查找。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     *
     * @param nums   数组
     * @param target 目标值
     */
    public static int binarySearchInsert(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    private static int binarySearch(int[] nums, int start, int end, int target) {
        if (start >= end) { // 没有找到该元素
            if (nums[end] >= target) {
                return end;
            } else {
                return end + 1;
            }
        }
        int mid = (start + end) / 2 + 1;
        if (nums[mid] == target) {
            return mid;
        }

        if (nums[mid] > target) {
            return binarySearch(nums, start, mid - 1, target);
        } else {
            return binarySearch(nums, mid + 1, end, target);
        }
    }

    /**
     * 合并两个有序数组。nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     *
     * @param nums1 数组1
     * @param m     数组1有效长度
     * @param nums2 数组2
     * @param n     数组2有效长度
     */
    public static void mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
        int index = nums1.length - 1;
        int l = m - 1;
        int r = n - 1;
        while (index >= 0) {
            while (l >= 0 && (r < 0 || nums1[l] >= nums2[r])) { // nums2已经遍历完或者nums1的最大值大于nums2的最大值
                nums1[index] = nums1[l];
                l--;
                index--;
            }
            while (r >= 0 && (l < 0 || nums2[r] >= nums1[l])) {
                nums1[index] = nums2[r];
                r--;
                index--;
            }
        }
    }


    public static void main(String[] args) {
        int[] ar = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        // int removeDuplicates = removeDuplicates(ar);
        // System.out.println(removeDuplicates);
        // System.out.println(Arrays.toString(ar));

        int[] ar2 = {0, 1, 2, 2, 3, 0, 4, 2};
        // removeDuplicates = removeDuplicates(ar2);
        // System.out.println(removeDuplicates);
        // System.out.println(Arrays.toString(ar2));
        //
        // int res = removeElement(ar2, 2);
        // System.out.println(res);
        // System.out.println(Arrays.toString(ar2));

        int[] arr3 = {1, 3, 5, 6};
        // System.out.println(binarySearchInsert(arr3, 5));
        //
        // int[] arr5 = {1, 3, 5, 6};
        // System.out.println(binarySearchInsert(arr5, 7));

        int[] arr4 = {1};
        // System.out.println(binarySearchInsert(arr4, 1));

        int[] arr6 = {1, 2, 3, 0, 0, 0};
        int[] arr7 = {2, 5, 6};
        // int[] arr6 = {4, 0, 0, 0, 0, 0};
        // int[] arr7 = {2, 3, 4, 5, 6};
        mergeSortedArray(arr6, 3, arr7, 3);
        System.out.println(Arrays.toString(arr6));
    }

}
