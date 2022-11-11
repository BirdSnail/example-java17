package birdsnail.example.算法.常见算法;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringAlgorithmTest {

    @Test
    void maxLength() {

        int[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0};
        StringAlgorithm.MaxLengthRes res = StringAlgorithm.maxLength(arr);
        assertEquals(3, res.length);
        assertEquals(12, res.index);

        int[] arr2 = {1};
        res = StringAlgorithm.maxLength(arr2);
        assertEquals(1, res.length);
        assertEquals(0, res.index);

        int[] arr3 = {1, 0, 0};
        res = StringAlgorithm.maxLength(arr3);
        assertEquals(1, res.length);
        assertEquals(0, res.index);


        int[] arr4 = {0, 1, 0};
        res = StringAlgorithm.maxLength(arr4);
        assertEquals(1, res.length);
        assertEquals(1, res.index);

        int[] arr5 = {0, 0, 0, 0};
        res = StringAlgorithm.maxLength(arr5);
        assertEquals(0, res.length);
        assertEquals(-1, res.index);

        int[] arr6 = {0, 1, 1, 0};
        res = StringAlgorithm.maxLength(arr6);
        assertEquals(2, res.length);
        assertEquals(1, res.index);

        int[] arr7 = {1, 1, 1, 1};
        res = StringAlgorithm.maxLength(arr7);
        assertEquals(4, res.length);
        assertEquals(0, res.index);

        int[] arr8 = {1, 1, 1, 1, 0, 1, 1};
        res = StringAlgorithm.maxLength(arr8);
        assertEquals(4, res.length);
        assertEquals(0, res.index);


    }
}