package birdsnail.example.limit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlidingWindowLimitTest {

    @Test
    void check() throws InterruptedException {
        SlidingWindowLimit slidingWindowLimit = new SlidingWindowLimit(5, 2);

        String k1 = "abd";
        String k2 = "ccc";
        int limit = 7;
        // 模拟请求5次
        for (int i = 0; i < 5; i++) {
            slidingWindowLimit.request(k1);
        }

        assertTrue(slidingWindowLimit.check(k1, limit));
        assertTrue(slidingWindowLimit.check(k2, limit));

        Thread.sleep(4000L);

        assertTrue(slidingWindowLimit.check(k1, limit));
        assertTrue(slidingWindowLimit.check(k2, limit));
        // 继续请求3次
        for (int i = 0; i < 3; i++) {
            slidingWindowLimit.request(k1);
        }
        assertFalse(slidingWindowLimit.check(k1, limit));
        assertTrue(slidingWindowLimit.check(k2, limit));

        // 开始的请求数已过期
        Thread.sleep(6000L);
        assertTrue(slidingWindowLimit.check(k1, limit));
        assertTrue(slidingWindowLimit.check(k2, limit));

        for (int i = 0; i < 10; i++) {
            slidingWindowLimit.request(k2);
        }
        assertFalse(slidingWindowLimit.check(k2, limit));
    }
}