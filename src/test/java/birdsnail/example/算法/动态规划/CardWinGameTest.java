package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardWinGameTest {

    private static int[] arr = {1, 100, 1};
    private static int[] arr2 = {1, 100, 1, 1};

    @Test
    void win1() {
        assertEquals(100, CardWinGame.win1(arr));
        assertEquals(101, CardWinGame.win1(arr2));
    }

    @Test
    void win2() {
        assertEquals(100, CardWinGame.win2(arr));
        assertEquals(101, CardWinGame.win2(arr2));
    }
}