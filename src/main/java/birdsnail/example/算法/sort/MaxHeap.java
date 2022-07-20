package birdsnail.example.算法.sort;

import java.util.Arrays;

/**
 * 大根堆
 */
public class MaxHeap {

    private final int limit;
    private final int[] array;
    private int heapSize;

    public MaxHeap(int limit) {
        this.limit = limit;
        this.array = new int[limit];
    }

    public void heapInsert(int num) {
        if (heapSize > limit) {
            throw new RuntimeException("超过堆大小");
        }

        int index = heapSize++;
        array[index] = num;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (array[parentIndex] >= array[index]) {
                break;
            }
            swap(array, parentIndex, index);
            index = parentIndex;
        }
    }

    public int size() {
        return heapSize;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public boolean isFull() {
        return heapSize == limit;
    }

    /**
     * 弹出堆顶的元素
     */
    public int heapPop() {
        if (heapSize < 0) {
            throw new RuntimeException("堆是空的");
        }

        int res = array[0];
        int index = 0;
        swap(array, --heapSize, index);
        SortCase.heapIfy(array, heapSize, index);

        return res;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public String toString() {
        return "MaxHeap{" +
                "array=" + Arrays.toString(Arrays.copyOf(array, heapSize)) +
                '}';
    }
}
