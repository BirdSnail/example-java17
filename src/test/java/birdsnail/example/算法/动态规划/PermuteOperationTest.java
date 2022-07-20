package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermuteOperationTest {


    @Test
    void permuteTest() {
        List<String> chars = List.of("a", "b", "c");
        List<String> charPermute = PermuteOperation.charPermute(chars);

        assertEquals(6, charPermute.size());
        assertTrue(charPermute.contains("abc"));
        assertTrue(charPermute.contains("bac"));
        assertTrue(charPermute.contains("bca"));
    }

}