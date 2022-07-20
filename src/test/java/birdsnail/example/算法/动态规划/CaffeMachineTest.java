package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaffeMachineTest {


    @Test
    void bestDrinkTimeTest() {
        int[] caffeMachine = {2, 3, 7};
        assertArrayEquals(new int[]{2, 3, 4, 6, 6}, CaffeMachine.bestDrinkTime(caffeMachine, 5));
        assertArrayEquals(new int[]{2}, CaffeMachine.bestDrinkTime(caffeMachine, 1));
        assertArrayEquals(new int[]{2, 3, 4}, CaffeMachine.bestDrinkTime(caffeMachine, 3));
    }

    @Test
    void cupCleanTest() {
        int[] drinks = {2, 3, 4};
        System.out.println(CaffeMachine.cupClean(drinks, 0, 0, 1, 2));
        assertEquals(
                CaffeMachine.cupClean(drinks, 0, 0, 1, 2),
                CaffeMachine.cupCleanDP(drinks, 1, 2));
    }

}