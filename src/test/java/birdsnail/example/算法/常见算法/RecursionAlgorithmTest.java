package birdsnail.example.算法.常见算法;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class RecursionAlgorithmTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    void reverse() {
        List<Integer> list = List.of(1, 2, 3, 4);
        Deque<Integer> stack = new LinkedList<>();
        for (Integer num : list) {
            stack.push(num);
        }

        RecursionAlgorithm.reverse(stack);
        for (Integer num : list) {
            Assertions.assertEquals(num, stack.pop());
        }
    }

    @Test
    void nQueenTest() {
        Assertions.assertEquals(1, RecursionAlgorithm.nQueensProblem(1));
        Assertions.assertEquals(0, RecursionAlgorithm.nQueensProblem(2));
        Assertions.assertEquals(0, RecursionAlgorithm.nQueensProblem(3));
        Assertions.assertEquals(2, RecursionAlgorithm.nQueensProblem(4));
        Assertions.assertEquals(92, RecursionAlgorithm.nQueensProblem(8));
    }

}