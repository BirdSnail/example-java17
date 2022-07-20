package birdsnail.example.算法.sort;

import java.util.Arrays;

public class MinHeap {

    private int[] arr;

    private int size = 0;

    public MinHeap() {
        this.arr = new int[10];
    }

    public MinHeap(int size) {
        this.arr = new int[size];
    }

    public MinHeap push(int num) {
        if (arr.length < size + 1) {
            this.arr = Arrays.copyOf(this.arr, this.arr.length * 2);
        }

        this.arr[size] = num;
        size++;
        // 较小值往上浮
        int curIndex = size - 1;
        int father = (curIndex - 1) / 2;
        while (father >= 0 ) {
            int left = 2 * father + 1;
            if (left > size - 1) {
                break;
            }
            int right = left + 1;
            int min = (right >= size || this.arr[left] <= this.arr[right]) ? left : right;

            if (this.arr[father] > this.arr[min]) {
                swap(father, min);
                father = (father - 1) / 2;
            } else {
                break;
            }
        }

        return this;
    }

    private void swap(int father, int min) {
        int temp = this.arr[father];
        this.arr[father] = this.arr[min];
        this.arr[min] = temp;
    }

    public void print() {
        if (size == 0) {
            System.out.println("[]");
        }

        int[] res = new int[size];
        int i = 0;
        while (this.size > 0) {
            int n = pop();
            res[i++] = n;
        }
        System.out.println(Arrays.toString(res));
    }

    public int pop() {
        if (size == 0) {
            throw new IllegalStateException("堆的大小为空");
        }

        int res = this.arr[0];
        swap(0, size - 1);
        size--;

        // 较大值往下沉
        int curIndex = 0;
        int left = 1;
        while (left < size) {
            int right = left + 1;
            int min = (right > size - 1 || this.arr[left] <= this.arr[right]) ? left : right;

            if (this.arr[curIndex] > this.arr[min]) {
                swap(curIndex, min);
                curIndex = min;
                left = 2 * curIndex + 1;
            } else {
                break;
            }
        }

        return res;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.push(9).push(2).push(0).push(100).push(52);

        for (int i = 100; i >= 0; i--) {
            minHeap.push(i);
        }

        minHeap.print();


        System.out.println((0 - 3) / 2);
    }
}