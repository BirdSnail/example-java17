package birdsnail.example.算法.数据结构;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 大顶堆, 仿照{@link PriorityQueue}类
 */
public class MaxHeap {

    private int size;

    private final int[] nums;


    /**
     * 指定堆的容量
     *
     * @param limit 堆的最大size
     */
    public MaxHeap(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("堆大小必须大于0");
        }
        this.nums = new int[limit];
    }

    /**
     * 数组构建堆结构
     */
    public MaxHeap(int[] arr) {
        this.nums = Arrays.copyOf(arr, arr.length);
        size = arr.length;
        heapify(nums);
    }

    public void insert(int value) {
        if (nums.length == size) {
            throw new RuntimeException("堆已满");
        }
        int i = size;
        siftUp(i, value);
        size++;
    }

    /**
     * 移除index位置的元素。0到index-1位置的元素不用改变
     *
     * @param index 移除的位置
     */
    public void remove(int index) {
        int s = --size;
        if (s == index) // removed last element
            nums[index] = -1;
        else {
            int moved = nums[s];
            nums[s] = -1; // 空出最后一个位置，代表移除了一个元素
            siftDown(index, moved);
            if (nums[index] == moved) { // 没有发生降级操作，要尝试向上提升。比如最后一个节点在右子树，删除了左子树的数据，删除节点移动到左子树时可能大于它的父节点
                siftUp(index, moved);
            }
        }
    }

    public Integer top() {
        int num = nums[0];
        remove(0);
        return num;
    }

    public Integer peek() {
        return nums[0];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size <= 0;
    }

    /**
     * 在i位置插入值value，如果它大于父节点，就将value沿着树不断向上提升，直到它是根节点或者小于等于父节点为止，以此保证堆的性质。
     *
     * @param index 插入位置
     * @param value 插入值
     */
    private void siftUp(int index, int value) {
        while (index > 0) {
            int parentIndex = (index - 1) >>> 1; // 父 = （cur - 1）/ 2
            int parent = nums[parentIndex];
            if (value <= parent) {
                break;
            }
            nums[index] = parent; // 父节点下移
            index = parentIndex;
        }
        nums[index] = value;
    }

    /**
     * 在index位置插入元素key，并将元素沿着树不断降级，直到它是叶子节点或者大于子节点，以此保持堆的性质
     *
     * @param index
     * @param key
     */
    private void siftDown(int index, int key) {
        int half = size >>> 1;           // loop while a non-leaf
        while (index < half) {
            int child = (index << 1) + 1; // 左孩子
            int c = nums[child];
            int right = child + 1; // 右孩子
            if (right < size && nums[right] > c) { // 取较大的子节点
                c = nums[child = right];
            }
            if (key >= c) { //
                break;
            }
            nums[index] = c;
            index = child;
        }
        nums[index] = key;
    }

    @Override
    public String toString() {
        return Arrays.toString(nums);
    }

    /**
     * 帮助一个数组构建成大顶堆
     *
     * @param arr 输入数组
     */
    private void heapify(int[] arr) {
        int half = (arr.length >>> 1) - 1;
        for (int i = half; i >= 0; i--) { // 从最后一层非叶子节点的第一个节点开始
            siftDown(i, arr[i]); // 较小的值会下沉，保证了当前节点的值一定当前子树的最大值
        }
    }

    public static void main(String[] args) {


    }

}
