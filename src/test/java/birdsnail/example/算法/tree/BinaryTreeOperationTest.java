package birdsnail.example.算法.tree;

import birdsnail.example.算法.数据结构.tree.BinaryTreeOperation;
import birdsnail.example.算法.数据结构.tree.TreeNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BinaryTreeOperationTest {

    @Test
    void widthTest() {
        int[] arr = {1, 3, 2, 5, 3, 2, 9, 8};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        int width = BinaryTreeOperation.widthOfBinaryTree(head);
        System.out.println(width);
    }


    @Test
    void getSuccessorNodeTest() {
        int[] arr = {1, 3, 2, 5, 3, 2, 9, 8};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        List<TreeNode> treeNodesWithMidSort = getAllTreeNodesByMidSort(head);

        TreeNode fourNode = treeNodesWithMidSort.get(4);
        TreeNode afterNode = BinaryTreeOperation.getSuccessorNode(fourNode);
        Assertions.assertSame(treeNodesWithMidSort.get(5), afterNode);

        for (int i = 0; i < treeNodesWithMidSort.size(); i++) {
            TreeNode expect = null;
            if (i != treeNodesWithMidSort.size() - 1) {
                expect = treeNodesWithMidSort.get(i + 1);
            }
            TreeNode srcNode = treeNodesWithMidSort.get(i);
            TreeNode successorNode = BinaryTreeOperation.getSuccessorNode(srcNode);
            Assertions.assertSame(expect, successorNode);
        }
    }

    private List<TreeNode> getAllTreeNodesByOriginOrder(TreeNode head) {
        List<TreeNode> result = new ArrayList<>();
        Queue<TreeNode> treeNodeQueue = new LinkedList<>();
        treeNodeQueue.add(head);
        while (!treeNodeQueue.isEmpty()) {
            TreeNode treeNode = treeNodeQueue.poll();
            if (treeNode.getLeft() != null) {
                treeNodeQueue.add(treeNode.getLeft());
                result.add(treeNode.getLeft());
            }
            if (treeNode.getRight() != null) {
                treeNodeQueue.add(treeNode.getRight());
                result.add(treeNode.getRight());
            }
        }

        return result;
    }

    // 中序遍历整个二叉树，将节点收集到集合中
    private List<TreeNode> getAllTreeNodesByMidSort(TreeNode head) {
        List<TreeNode> result = new ArrayList<>();
        collectTreeNodeByMidSort(head, result);
        return result;
    }

    private void collectTreeNodeByMidSort(TreeNode head, List<TreeNode> collector) {
        if (head == null) {
            return;
        }

        collectTreeNodeByMidSort(head.getLeft(), collector);
        collector.add(head);
        collectTreeNodeByMidSort(head.getRight(), collector);
    }

    @Test
    void isBalanceTreeTest() {
        Assertions.assertTrue(BinaryTreeOperation.isBalanceTree(null));
        int[] arr = {1, 3, 2, 5, 3, 2, 9, 8};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        Assertions.assertTrue(BinaryTreeOperation.isBalanceTree(head));

        TreeNode one = TreeNode.of(3);
        TreeNode two = TreeNode.of(2);
        one.setLeft(two);
        Assertions.assertTrue(BinaryTreeOperation.isBalanceTree(one));
        TreeNode three = TreeNode.of(3);
        two.setRight(three);
        Assertions.assertFalse(BinaryTreeOperation.isBalanceTree(one));
    }

    @Test
    void findBinaryTreeNodeTest() {
        TreeNode sbtHead = BinaryTreeOperation.findSearchBinaryTreeHead(null);
        Assertions.assertNull(sbtHead);

        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        four.setLeft(null);
        four.setRight(five);
        sbtHead = BinaryTreeOperation.findSearchBinaryTreeHead(four);
        Assertions.assertSame(four, sbtHead);

        int[] arr = {10, 5, 16, 4, 7, 12, 18};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);
        sbtHead = BinaryTreeOperation.findSearchBinaryTreeHead(head);
        Assertions.assertSame(head, sbtHead);

        int[] arr1 = {10, 5, 16, 4, 7, 19, 18};
        head = TreeNode.createBinaryTreeFromArray(arr1);
        sbtHead = BinaryTreeOperation.findSearchBinaryTreeHead(head);
        Assertions.assertSame(head.getLeft(), sbtHead);

        int[] arr2 = {10, 5, 16, 4, 3, 12, 18};
        head = TreeNode.createBinaryTreeFromArray(arr2);
        sbtHead = BinaryTreeOperation.findSearchBinaryTreeHead(head);
        Assertions.assertSame(head.getRight(), sbtHead);
    }

    @Test
    void lowestCommonAncestorTest() {
        int[] arr = {10, 5, 16, 4, 7, 12, 18, 12, 234, 23, 435, 32, 3, 76};
        TreeNode head = TreeNode.createBinaryTreeFromArray(arr);

        TreeNode ancestor = BinaryTreeOperation.lowestCommonAncestor(head, head.getLeft(), head.getRight().getRight());
        Assertions.assertSame(head, ancestor);

        ancestor = BinaryTreeOperation.lowestCommonAncestor(head.getLeft(), head.getLeft().getLeft(), head.getLeft().getRight().getRight());
        Assertions.assertSame(head.getLeft(), ancestor);

        ancestor = BinaryTreeOperation.lowestCommonAncestor(head.getLeft(), head.getLeft().getRight(), head.getLeft().getRight().getRight());
        Assertions.assertSame(head.getLeft().getRight(), ancestor);
    }
}