package birdsnail.example.算法.动态规划;

import org.junit.jupiter.api.Test;

import java.util.List;

import birdsnail.example.entity.ProjectInfo;

import static org.junit.jupiter.api.Assertions.*;

class GreedyOperationTest {

    @Test
    void findMaxMoney() {
        List<ProjectInfo> projectInfos = List.of(new ProjectInfo(10, 10),
                new ProjectInfo(5, 6),
                new ProjectInfo(100, 500));

        int maxMoney = GreedyOperation.findMaxMoney(projectInfos, 2, 50);
        assertEquals(66, maxMoney);

        maxMoney = GreedyOperation.findMaxMoney(projectInfos, 2, 3);
        assertEquals(3, maxMoney);

        maxMoney = GreedyOperation.findMaxMoney(projectInfos, 1, 100);
        assertEquals(600, maxMoney);

        maxMoney = GreedyOperation.findMaxMoney(projectInfos, 2, 100);
        assertEquals(610, maxMoney);
    }



}