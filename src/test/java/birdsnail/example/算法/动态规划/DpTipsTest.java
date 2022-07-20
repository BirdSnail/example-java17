package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DpTipsTest {


    @Test
    void coinsMoney() {
        int[] coins = {1, 2, 5};
        assertEquals(4, DpTips.coinsWaysNoLimit(coins, 5));
        assertEquals(4, DpTips.coinsWaysNoLimitDp(coins, 5));

        coins = new int[]{1, 5};
        assertEquals(2, DpTips.coinsWaysNoLimit(coins, 5));
        assertEquals(2, DpTips.coinsWaysNoLimitDp(coins, 5));
    }


    @Test
    void coinsMoney3() {
        int[] coins = {1, 1, 1};
        assertEquals(1, DpTips.coinsWays3(coins, 3));
        assertEquals(1, DpTips.coinsWays3Dp(coins, 3));

        assertEquals(1, DpTips.coinsWays3(coins, 2));
        assertEquals(1, DpTips.coinsWays3Dp(coins, 2));

        assertEquals(0, DpTips.coinsWays3(coins, 5));
        assertEquals(0, DpTips.coinsWays3Dp(coins, 5));

        coins = new int[]{1, 2, 1, 1, 2, 1, 2};
        assertEquals(3, DpTips.coinsWays3(coins, 4));
        assertEquals(DpTips.coinsWays3Dp(coins, 4), DpTips.coinsWays3(coins, 4));

        assertEquals(2, DpTips.coinsWays3(coins, 5));
        assertEquals(2, DpTips.coinsWays3Dp(coins, 5));

        assertEquals(2, DpTips.coinsWays3(coins, 7));
        assertEquals(2, DpTips.coinsWays3Dp(coins, 7));

    }

    @Test
    void killTest() {
        assertEquals(1 / Math.pow(4, 3), DpTips.killProbability(9, 3, 3));
        assertEquals(1 / Math.pow(5, 5), DpTips.killProbability(20, 4, 5));

        assertEquals(1 / Math.pow(4, 3), DpTips.killProbabilityDp(9, 3, 3));
        assertEquals(1 / Math.pow(5, 5), DpTips.killProbabilityDp(20, 4, 5));
    }

    @Test
    void minNumOfCoinsTest() {
        int[] coins = {1, 2, 5};
        int amount = 11;
        assertEquals(3, DpTips.minNumberOfCoins(coins, amount));
        assertEquals(3, DpTips.minNumberOfCoinsDp(coins,amount));
    }

}