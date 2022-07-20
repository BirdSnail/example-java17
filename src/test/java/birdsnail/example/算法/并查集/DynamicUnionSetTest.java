package birdsnail.example.算法.并查集;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import birdsnail.example.entity.Point;

class DynamicUnionSetTest {

    private IUnionSet<Integer> unionSet;

    @BeforeEach
    void init() {
        unionSet = new DynamicUnionSet<>();
    }

    @Test
    void union() {
        unionSet.union(0, null);
        Assertions.assertFalse(unionSet.isSameSet(0, 1));

        unionSet.union(0, 1);
        Assertions.assertTrue(unionSet.isSameSet(0, 1));
        Assertions.assertEquals(1, unionSet.getIsolationNums());
        Assertions.assertEquals(2, unionSet.getSize(0));

        unionSet.union(1, 2);
        Assertions.assertTrue(unionSet.isSameSet(0, 2));
        Assertions.assertEquals(1, unionSet.getIsolationNums());
        Assertions.assertFalse(unionSet.isSameSet(3, 2));
        Assertions.assertEquals(3, unionSet.getSize(0));
        Assertions.assertEquals(3, unionSet.getSize(2));

        unionSet.union(3, 6);
        unionSet.union(3, 7);
        unionSet.union(3, 8);
        unionSet.union(3, 9);
        Assertions.assertTrue(unionSet.isSameSet(3, 8));
        Assertions.assertFalse(unionSet.isSameSet(3, 2));
        Assertions.assertEquals(2, unionSet.getIsolationNums());
        Assertions.assertEquals(5, unionSet.getSize(3));
        Assertions.assertEquals(5, unionSet.getSize(7));

        unionSet.union(9, 0);
        Assertions.assertTrue(unionSet.isSameSet(3, 2));
        Assertions.assertTrue(unionSet.isSameSet(0, 7));
        Assertions.assertFalse(unionSet.isSameSet(0, 20));
        Assertions.assertFalse(unionSet.isSameSet(8, 20));

        Assertions.assertEquals(1, unionSet.getIsolationNums());
        Assertions.assertEquals(8, unionSet.getSize(7));
    }

    @Test
    void dynamicUnion() {
        int row = 5;
        int col = 5;
        int[][] grip = initGrip(row, col);

        int[][] lightLocation = {{1, 1}, {2, 1}, {1, 3}, {2, 4}, {3, 4}, {2, 3}, {4, 0}};

        List<Integer> result = new ArrayList<>();

        for (int[] point : lightLocation) {
            int i = point[0];
            int j = point[1];
            grip[i][j] = 1;

            // 检查上下左右是否可以合并
            Point curPoint = new Point(i, j);
            unionSet.appendIfAbsent(getLocalNum(curPoint, col));
            unionIfNecessary(curPoint, new Point(i, j + 1), grip);
            unionIfNecessary(curPoint, new Point(i, j - 1), grip);
            unionIfNecessary(curPoint, new Point(i + 1, j), grip);
            unionIfNecessary(curPoint, new Point(i - 1, j), grip);

            result.add(unionSet.getIsolationNums());
        }

        Assertions.assertEquals(List.of(1, 1, 2, 3, 3, 2, 3), result);
    }

    private void unionIfNecessary(Point one, Point two, int[][] grip) {
        int rowMax = grip.length;
        int colMax = grip[0].length;

        if (one.getX() >= rowMax || one.getX() < 0 || one.getY() >= colMax || one.getY() < 0) {
            return;
        }
        if (two.getX() >= rowMax || two.getX() < 0 || two.getY() >= colMax || two.getY() < 0) {
            return;
        }

        if (grip[one.getX()][one.getY()] == 1 && grip[two.getX()][two.getY()] == 1) {
            int num1 = getLocalNum(one, colMax);
            int num2 = getLocalNum(two, colMax);
            unionSet.union(num1, num2);
        }
    }

    private int getLocalNum(Point point, int colNums) {
        return colNums * point.getX() + point.getY();
    }

    private int[][] initGrip(int row, int col) {
        int[][] grip = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grip[i][j] = 0;
            }
        }
        return grip;
    }


}