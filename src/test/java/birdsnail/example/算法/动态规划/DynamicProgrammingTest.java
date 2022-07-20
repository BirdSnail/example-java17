package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import static birdsnail.example.算法.动态规划.DynamicProgramming.*;
import static org.junit.jupiter.api.Assertions.*;

class DynamicProgrammingTest {

    @Test
    void longestCommonSubsequenceLengthTest() {
        String first = "AeVBHC";
        String second = "AeBHC";
        assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength2(first, second));
        assertEquals(5, longestCommonSubsequenceLength(first, second));
        assertEquals(5, longestCommonSubsequenceLength2(first, second));

        first = "ac";
        second = "bsd";
        assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength2(first, second));
        assertEquals(0, longestCommonSubsequenceLength(first, second));
        assertEquals(0, longestCommonSubsequenceLength2(first, second));

        first = "a";
        second = "basd";
        assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength2(first, second));
        assertEquals(1, longestCommonSubsequenceLength(first, second));
        assertEquals(1, longestCommonSubsequenceLength2(first, second));

        first = "afsadfe";
        second = "f";
        assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength2(first, second));
        assertEquals(1, longestCommonSubsequenceLength(first, second));
        assertEquals(1, longestCommonSubsequenceLength2(first, second));


        first = "fgfakjkfjslkfdfsf";
        second = "ejkjklfalkfsdffsafsdfjkjs";
        // assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength3(first, second));
        assertEquals(longestCommonSubsequenceLength(first, second), longestCommonSubsequenceLength4(first, second));
    }

    @Test
    void splitArrayTest() {
        int[] arr = {100, 1, 2, 5};
        assertEquals(8, DynamicProgramming.splitArrayDp(arr));
        assertEquals(8, DynamicProgramming.splitArray(arr));

        arr = new int[]{5, 5};
        assertEquals(5, DynamicProgramming.splitArrayDp(arr));
        assertEquals(5, DynamicProgramming.splitArray(arr));
    }


    @Test
    void minSubSplitArrayTest() {
        int[] arr = {100, 1, 2, 5};
        assertEquals(7, DynamicProgramming.minSubArraySum(arr));
        assertEquals(7, DynamicProgramming.minSubArraySumDp(arr));

        arr = new int[]{10, 1, 3};
        assertEquals(4, DynamicProgramming.minSubArraySum(arr));
        assertEquals(4, DynamicProgramming.minSubArraySumDp(arr));

        arr = new int[]{10, 1, 3, 5, 20};
        assertEquals(18, DynamicProgramming.minSubArraySum(arr));
        assertEquals(18, DynamicProgramming.minSubArraySumDp(arr));

        arr = new int[]{1, 1, 1, 1};
        assertEquals(2, DynamicProgramming.minSubArraySum(arr));
        assertEquals(2, DynamicProgramming.minSubArraySumDp(arr));

        arr = new int[]{1, 1};
        assertEquals(1, DynamicProgramming.minSubArraySum(arr));
        assertEquals(1, DynamicProgramming.minSubArraySumDp(arr));
    }

}