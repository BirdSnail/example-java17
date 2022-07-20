package birdsnail.example.算法.sort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {


    @Test
    void heapInsertTest() {
        MaxHeap maxHeap = new MaxHeap(6);
        maxHeap.heapInsert(0);
        maxHeap.heapInsert(1);
        maxHeap.heapInsert(1);
        maxHeap.heapInsert(2);
        maxHeap.heapInsert(3);
        System.out.println(maxHeap);

        assertEquals(5, maxHeap.size());
        assertFalse(maxHeap.isEmpty());
        assertFalse(maxHeap.isFull());

        assertEquals(3, maxHeap.heapPop());
        assertEquals(2, maxHeap.heapPop());
        assertEquals(1, maxHeap.heapPop());
        assertEquals(1, maxHeap.heapPop());
        assertEquals(0, maxHeap.heapPop());
        assertTrue(maxHeap.isEmpty());
        assertThrows(RuntimeException.class, maxHeap::heapPop);
    }

}