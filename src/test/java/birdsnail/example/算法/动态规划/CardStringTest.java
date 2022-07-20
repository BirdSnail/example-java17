package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardStringTest {


    @Test
    void cardString() {
        String value = "aabb";
        List<String> cards = List.of("aa", "b", "abcd", "dd");

        assertEquals(2, CardString.solution(value, cards));
        assertEquals(Integer.MAX_VALUE, CardString.solution("k", cards));
        assertEquals(1, CardString.solution("ca", cards));
        assertEquals(2, CardString.solution("dddd", cards));

        assertEquals(2, CardString.solution2(value, cards));
        assertEquals(Integer.MAX_VALUE, CardString.solution2("k", cards));
        assertEquals(1, CardString.solution2("ca", cards));
        assertEquals(2, CardString.solution2("dddd", cards));

        assertEquals(2, CardString.solution3(value, cards));
        assertEquals(Integer.MAX_VALUE, CardString.solution3("k", cards));
        assertEquals(1, CardString.solution3("ca", cards));
        assertEquals(2, CardString.solution3("dddd", cards));
    }

}