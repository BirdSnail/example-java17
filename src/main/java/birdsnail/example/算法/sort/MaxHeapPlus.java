package birdsnail.example.算法.sort;

import java.util.*;

/**
 * 加强大根堆
 */
public class MaxHeapPlus<T> {

    private List<T> heap;

    /**
     * 反向索引map，保存元素对应的index
     */
    private Map<T, Integer> indexMap;

    /**
     * 堆大小
     */
    private int heapSize;

    private final Comparator<T> comparator;


    public MaxHeapPlus(List<T> elements, Comparator<T> comparator) {
        this.heap = new ArrayList<>(elements.size());
        this.indexMap = new HashMap<>(elements.size());
        heapSize = 0;
        this.comparator = comparator;

        for (T element : elements) {
            push(element);
        }
    }

    public MaxHeapPlus(int size, Comparator<T> comparator) {
        this.heap = new ArrayList<>(size);
        this.indexMap = new HashMap<>(size);
        heapSize = 0;
        this.comparator = comparator;
    }

    /**
     * 向堆中添加一个元素
     *
     * @param obj 添加的对象
     */
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    // 较大的值往上浮
    private void heapInsert(int index) {
        int parent = (index - 1) / 2;
        while (parent >= 0 && comparator.compare(heap.get(index), heap.get(parent)) > 0) {
            swap(heap, parent, index);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    /**
     * 弹出堆顶元素
     *
     * @return 堆顶元素
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T old = heap.get(0);
        swap(heap, 0, --heapSize);
        heap.remove(heapSize);
        indexMap.remove(old);

        heapify(0);

        return old;
    }

    // 下沉较小的值
    private void heapify(int index) {
        int left = 2 * index + 1;
        while (left < heapSize) {
            int largest = getSubLargest(left);
            if (comparator.compare(heap.get(index), heap.get(largest)) < 0) {
                swap(heap, largest, index);
                index = largest;
                left = 2 * index + 1;
            } else {
                break;
            }
        }
    }

    private int getSubLargest(int index) {
        int right = index + 1;
        if (right >= heapSize) { // 只有左节点
            return index;
        }
        return comparator.compare(heap.get(index), heap.get(right)) > 0 ? index : right;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int getSize() {
        return heapSize;
    }

    /**
     * 移除元素，加强堆的特殊功能，在移除一个元素后，依然能保持大根堆的性质，Java提供的堆实现{@link PriorityQueue}在删除时会进行遍历操作，
     * 但是我们有了反向索引表后可以直接定位到元素在堆中的index。
     *
     * @param obj 待移除的元素
     */
    public void remove(T obj) {
        T replace = heap.get(--heapSize); // 用最后一个元素替代被删除的元素
        Integer index = indexMap.get(obj);
        swap(heap, heapSize, index);
        heap.remove(heapSize);
        indexMap.remove(obj);
        if (replace != obj) {
            /*
                隐藏逻辑：以下两个方法只会执行一个，要么替代的元素比父节点大，往上浮，要么比子节点小，往下沉
             */
            heapify(index);
            heapInsert(index);
        }
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    private void swap(List<T> list, int first, int second) {
        T one = list.get(first);
        T two = list.get(second);
        list.set(first, two);
        list.set(second, one);

        indexMap.put(one, second);
        indexMap.put(two, first);
    }


}
