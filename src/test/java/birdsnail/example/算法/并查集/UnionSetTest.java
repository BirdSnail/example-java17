package birdsnail.example.算法.并查集;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UnionSetTest {

    private DefaultUnionSet<Integer> unionSet;

    @BeforeEach
    void setUp() {
        List<Integer> numbers = IntStream.range(0, 50).boxed().collect(Collectors.toList());
        unionSet = new DefaultUnionSet<>(numbers);
    }


    @Test
    void operationTest() {
        Assertions.assertFalse(unionSet.isSameSet(0, 1));

        unionSet.union(0 ,1);
        Assertions.assertTrue(unionSet.isSameSet(0, 1));
        Assertions.assertEquals(49, unionSet.getIsolationNums());
        Assertions.assertEquals(2, unionSet.getSize(0));

        unionSet.union(1, 2);
        Assertions.assertTrue(unionSet.isSameSet(0, 2));
        Assertions.assertEquals(48, unionSet.getIsolationNums());
        Assertions.assertFalse(unionSet.isSameSet(3, 2));
        Assertions.assertEquals(3, unionSet.getSize(0));
        Assertions.assertEquals(3, unionSet.getSize(2));

        unionSet.union(3, 6);
        unionSet.union(3, 7);
        unionSet.union(3, 8);
        unionSet.union(3, 9);
        Assertions.assertTrue(unionSet.isSameSet(3, 8));
        Assertions.assertFalse(unionSet.isSameSet(3, 2));
        Assertions.assertEquals(44, unionSet.getIsolationNums());
        Assertions.assertEquals(5, unionSet.getSize(3));
        Assertions.assertEquals(5, unionSet.getSize(7));

        unionSet.union(9, 0);
        Assertions.assertTrue(unionSet.isSameSet(3, 2));
        Assertions.assertTrue(unionSet.isSameSet(0, 7));
        Assertions.assertFalse(unionSet.isSameSet(0, 20));
        Assertions.assertFalse(unionSet.isSameSet(8, 20));

        Assertions.assertEquals(43, unionSet.getIsolationNums());
        Assertions.assertEquals(8, unionSet.getSize(7));

    }


}