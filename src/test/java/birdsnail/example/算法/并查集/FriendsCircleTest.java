package birdsnail.example.算法.并查集;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import birdsnail.example.flow.Pair;

class FriendsCircleTest {

    @Test
    void findFriendsCircleSize() {
        List<Pair> pairs = List.of(
                Pair.of(0, 1),
                // Pair.of(0, 3),
                Pair.of(2, 1),
                Pair.of(3, 4));
        int[][] initData = getData(5, pairs);

        int result = UnionSetQuestion.findFriendsCircleSize(initData);
        Assertions.assertEquals(2, result);

        initData = getData(5, Collections.emptyList());
        result = UnionSetQuestion.findFriendsCircleSize(initData);
        Assertions.assertEquals(5, result);

    }

    private int[][] getData(int size, List<Pair> pairs) {
        int[][] data = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    data[i][j] = 1;
                }
            }
        }

        for (Pair pair : pairs) {
            data[pair.getOne()][pair.getTwo()] = 1;
            data[pair.getTwo()][pair.getOne()] = 1;
        }

        return data;
    }
}