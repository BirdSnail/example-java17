package birdsnail.example.算法.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {

    @Test
    void createTest() {
        int[] arr = {1, 3, 2, 5, 3, 2};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        System.out.println(head.getValue());
    }


}