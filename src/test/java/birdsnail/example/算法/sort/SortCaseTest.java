package birdsnail.example.算法.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SortCaseTest {

    int[] one;
    int[] two;
    int[] arr = {0, 2, 9, 8, 4, 3};

    @BeforeEach
    void setUp() {
        one = new int[]{0, 1, 5};
        two = new int[]{0, 4, 6};
    }

    @Test
    void mergeSortArray() {
        int[] sortArray = SortCase.mergeSortArray(one, two);
        System.out.println(Arrays.toString(sortArray));
        assertEquals(one.length + two.length, sortArray.length);
        assertEquals(0, sortArray[0]);
        assertEquals(1, sortArray[2]);
        assertEquals(6, sortArray[5]);
    }


    @Test
    void miniSumTest() {
        int hardResult = getHardCodeResult(one);
        int sum = SortCase.miniSumWithMergeSort(one);
        assertEquals(hardResult, sum);

        hardResult = getHardCodeResult(two);
        sum = SortCase.miniSumWithMergeSort(two);
        assertEquals(hardResult, sum);

        hardResult = getHardCodeResult(arr);
        sum = SortCase.miniSumWithMergeSort(arr);
        assertEquals(hardResult, sum);
    }

    // 通过暴力循环的方式获取数组最小和
    private int getHardCodeResult(int[] arr) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            int base = arr[i];
            for (int j = 0; j < i; j++) {
                if (base > arr[j]) {
                    result += arr[j];
                }
            }
        }

        return result;
    }

    @Test
    void quackSortTest() {
        SortCase.quackSort(one);
        System.out.println(Arrays.toString(one));
        assertEquals(0, one[0]);
        assertEquals(1, one[1]);
        assertEquals(5, one[2]);

        SortCase.quackSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void heapSortTest() {
        SortCase.heapSort(one);
        System.out.println(Arrays.toString(one));

        SortCase.heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void getDigitNumberTest() {
        assertEquals(1, SortCase.getDigitNum(1, 1));
        assertEquals(0, SortCase.getDigitNum(10, 1));
        assertEquals(2, SortCase.getDigitNum(2, 1));
        assertEquals(5, SortCase.getDigitNum(15, 1));

        assertEquals(0, SortCase.getDigitNum(900, 2));
        assertEquals(1, SortCase.getDigitNum(12, 2));
        assertEquals(0, SortCase.getDigitNum(4, 2));

        assertEquals(4, SortCase.getDigitNum(435, 3));
        assertEquals(6, SortCase.getDigitNum(1654, 3));
        assertEquals(9, SortCase.getDigitNum(940, 3));
    }

    @Test
    void getDigitTest() {
        assertEquals(1, SortCase.getDigit(1));
        assertEquals(1, SortCase.getDigit(0));
        assertEquals(1, SortCase.getDigit(2));
        assertEquals(2, SortCase.getDigit(18));
        assertEquals(2, SortCase.getDigit(10));
        assertEquals(2, SortCase.getDigit(22));
    }

    @Test
    void baseSort() {
        SortCase.baseSort(one);
        System.out.println(Arrays.toString(one));
        assertEquals(0, one[0]);
        assertEquals(1, one[1]);
        assertEquals(5, one[2]);

        SortCase.baseSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}