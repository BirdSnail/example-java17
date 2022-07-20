package birdsnail.example.算法.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 各种排序算法案例
 */
public class SortCase {

    private static final Random random = new Random();

    /**
     * 归并排序，合并两个有序数组
     *
     * @param oneArr 有序数组一
     * @param twoArr 有序数组二
     */
    public static int[] mergeSortArray(int[] oneArr, int[] twoArr) {
        int[] result = new int[oneArr.length + twoArr.length];
        int onePoint = 0, twoPoint = 0, i = 0;
        while (onePoint < oneArr.length && twoPoint < twoArr.length) {
            result[i++] = (oneArr[onePoint] <= twoArr[twoPoint]) ? oneArr[onePoint++] : twoArr[twoPoint++];
        }

        // 剩余的没有遍历完的数组
        while (onePoint < oneArr.length) {
            result[i++] = oneArr[onePoint++];
        }
        while (twoPoint < twoArr.length) {
            result[i++] = twoArr[twoPoint++];
        }

        return result;
    }


    /**
     * 利用归并排序求数组最小和
     */
    public static int miniSumWithMergeSort(int[] array) {
        if (array.length == 1) {
            return array[0];
        }

        return process(array, 0, array.length - 1);
    }

    public static int process(int[] array, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);
        return process(array, left, mid) + process(array, mid + 1, right) + merge(array, left, mid, right);
    }

    public static int merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];

        int result = 0;
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;

        while (p1 <= mid && p2 <= right) {
            int singleResult = arr[p1] < arr[p2] ? arr[p1] * (right - p2 + 1) : 0;
            result += singleResult;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }

        System.arraycopy(help, 0, arr, left, help.length);

        return result;
    }

    /**
     * 快速排序
     */
    public static void quackSort(int[] array) {
        quackSort(array, 0, array.length - 1);
    }

    private static void quackSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int base = getBase(arr, left, right);
        int[] partition = partition(arr, left, right, base);
        quackSort(arr, left, partition[0] - 1);
        quackSort(arr, partition[1] + 1, right);
    }

    private static int[] partition(int[] arr, int left, int right, int base) {
        int i = left;
        int max = right + 1;
        int less = left - 1;
        while (i < max) {
            if (arr[i] < base) {
                i++;
                less++;
            } else if (arr[i] == base) {
                i++;
            } else {
                swap(arr, i, --max);
            }
        }

        return new int[]{less + 1, max - 1};
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int getBase(int[] arr, int left, int right) {
        int index = random.nextInt(left, right + 1);
        return arr[index];
    }


    /**
     * 推排序
     */
    public static void heapSort(int[] array) {
        int heapSize = array.length;
        // 1.创建推, 从最后一个节点的父节点开始,不断向上
        for (int i = (array.length - 2) / 2; i >= 0; i--) {
            heapIfy(array, heapSize, i);
        }
        // 2.弹出推顶
        while (heapSize > 0) {
            swap(array, 0, --heapSize);
            heapIfy(array, heapSize, 0);
        }
    }

    // 下沉堆顶较小的元素
    public static void heapIfy(int[] arr, int heapSize, int index) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int right = left + 1;
            int max = (right >= heapSize || arr[left] >= arr[right]) ? left : right;// 值较大的叶子节点索引, 当右节点超过堆大小时直接取左节点

            if (arr[index] < arr[max]) {
                swap(arr, index, max);
                index = max;
                left = 2 * index + 1;
            } else {
                // 当前堆顶元素就是当前子树的最大元素，不需要向下比较了，直接退出对子树的遍历
                break;
            }
        }
    }

    // ==============================桶排序相关===============================================

    /**
     * 基数排序，桶排序思想的一种
     */
    public static void baseSort(int[] array) {
        int max = 0;
        for (int num : array) {
            max = Math.max(num, max);
        }

        int[] help = new int[array.length];
        int[] count = new int[10];

        // 从个位数开始遍历
        int maxDigit = getDigit(max);
        for (int digit = 1; digit <= maxDigit; digit++) {
            Arrays.fill(count, 0);
            // 统计当前位数数字相同的词频
            for (int i : array) {
                int digitNum = getDigitNum(i, digit);
                count[digitNum]++;
            }

            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }

            // 根据指定位数的数字做好排序，本质是放入特定编号的桶中
            for (int i = array.length - 1; i >= 0; i--) {
                int digitNum = getDigitNum(array[i], digit);
                int index = --count[digitNum];
                help[index] = array[i];
            }

            System.arraycopy(help, 0, array, 0, array.length);
        }
    }

    public static int getDigitNum(int number, int digit) {
        int res = 0;
        for (int i = 0; i < digit; i++) {
            res = number % 10;
            number = number / 10;
        }
        return res;
    }


    public static int getDigit(int number) {
        if (number == 0) {
            return 1;
        }
        return (int) (Math.log10(number) + 1);
    }
}
