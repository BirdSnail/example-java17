package birdsnail.example.算法.数据结构;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    @Test
    void add() {
        SkipList skipList = new SkipList();
        skipList.add("发顺丰", 10.0);
        skipList.add("发顺丰2", 8.0);
        skipList.add("发顺丰7", 8.0);
        skipList.add("发顺丰3", 500.0);
        skipList.add("发顺丰4", 200.0);
        skipList.add("发顺丰5", 56.0);
        skipList.add("发顺丰6", 235.0);

        skipList.printSkipList();
    }

    @Test
    void search() {
        for (int i = 0; i < 500; i++) {
            SkipList skipList = new SkipList();
            skipList.add("发顺丰", 10.0);
            skipList.add("发顺丰2", 8.0);
            skipList.add("发顺丰2", 9.0);
            skipList.add("发顺丰2", 7.0);
            skipList.printSkipList();
            SkipList.SkipListNode find = skipList.searchByScore(9.0);
            assertNotNull(find);
            assertEquals(find.getScore(), 9.0);
        }
    }

}