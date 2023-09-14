package birdsnail.example.算法.tree;

import birdsnail.example.算法.数据结构.tree.TreeNode;
import org.junit.jupiter.api.Test;

class TreeNodeTest {

    @Test
    void createTest() {
        int[] arr = {1, 3, 2, 5, 3, 2};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        System.out.println(head.getValue());
    }


}