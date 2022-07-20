package birdsnail.example.算法.sort;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxHeapPlusTest {


    @Test
    void heapTest() {
        List<Integer> source = List.of(1, 52, 2364, 32, 41, 10, 4, 78);
        MaxHeapPlus<Integer> heap = new MaxHeapPlus<>(source, Integer::compare);
        assertEquals(heap.getSize(), source.size());

        List<Integer> newList = source.stream().sorted(Comparator.comparing(Integer::intValue, Comparator.reverseOrder())).toList();
        int index = 0;
        while (!heap.isEmpty()) {
            assertEquals(newList.get(index++), heap.pop());
        }

        heap.push(100);
        heap.push(103);
        heap.push(107);
        heap.push(110);
        assertEquals(4, heap.getSize());
        assertEquals(110, heap.pop());

        heap.push(58);
        assertEquals(4, heap.getSize());
        assertEquals(107, heap.pop());
        assertEquals(103, heap.pop());
        assertEquals(2, heap.getSize());

        heap.push(200);
        heap.push(255);
        heap.push(201);
        heap.push(18);
        heap.push(19);
        heap.remove(255);
        assertEquals(201, heap.pop());
        heap.remove(200);
        assertEquals(100, heap.peek());

    }


}