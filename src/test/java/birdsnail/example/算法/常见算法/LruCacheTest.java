package birdsnail.example.算法.常见算法;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LruCacheTest {

    LruCache lruCache;

    @BeforeEach
    public void init() {
        lruCache = new LruCache(10);
        for (int i = 0; i < 11; i++) {
            lruCache.put(i, numStr(i));
        }
    }

    @Test
    void put() {
        assertEquals(10, lruCache.getSize());
        assertEquals(numStr(10), lruCache.getFirst());
        assertEquals(numStr(1), lruCache.getLast());

        lruCache.put(5, numStr(5));
        assertEquals(10, lruCache.getSize());
        assertEquals(numStr(5), lruCache.getFirst());
        assertEquals(numStr(1), lruCache.getLast());

        lruCache.put(20, numStr(20));
        assertEquals(10, lruCache.getSize());
        assertEquals(numStr(20), lruCache.getFirst());
        assertEquals(numStr(2), lruCache.getLast());
    }

    @Test
    void get() {
        Object o = lruCache.get(5);
        assertNotNull(o);
        assertEquals(10, lruCache.getSize());
        assertEquals(numStr(5), o);
        assertEquals(numStr(5), lruCache.getFirst());
    }

    private String numStr(int num) {
        return "num:" + num;
    }

}