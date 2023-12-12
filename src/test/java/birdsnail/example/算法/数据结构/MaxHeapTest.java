package birdsnail.example.算法.数据结构;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxHeapTest {


    @Test
    public void testInsert() {
        int[] arr = {0, 1, 2, 5, 6};
        MaxHeap heap = new MaxHeap(arr.length);
        for (int i : arr) {
            heap.insert(i);
        }
        System.out.println(heap);
        Arrays.sort(arr);

        for (int i = arr.length - 1; i > 0; i--) {
            assertEquals(arr[i], heap.top());
        }
    }

    @Test
    public void testHeapify() {
        int[] arr = {0, 1, 2, 5, 6};
        MaxHeap heap = new MaxHeap(arr);
        System.out.println(heap);
        Arrays.sort(arr);

        for (int i = arr.length - 1; i > 0; i--) {
            assertEquals(arr[i], heap.top());
        }
    }

    /**
     * 测试大根堆保存最小的k个元素
     */
    @Test
    public void testTopKAsc() {
        int[] arr = new int[100];
        int limit = 10;
        MaxHeap heap = new MaxHeap(limit);
        for (int i = 0; i < 100; i++) {
            int num = RandomUtils.nextInt(0, 500);
            arr[i] = num;
            if (i < limit) {
                heap.insert(num);
            } else if (num < heap.peek()) {
                heap.top();
                heap.insert(num);
            }
        }

        Arrays.sort(arr);
        int[] sortArr = Arrays.copyOf(arr, 10);
        System.out.println(Arrays.toString(sortArr));
        for (int i = sortArr.length - 1; i >= 0; i--) {
            assertEquals(sortArr[i], heap.top());
        }
    }


}