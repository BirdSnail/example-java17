package birdsnail.example.算法.tree;

import lombok.Data;

@Data
public class TreeNode {

    private int value;
    private TreeNode left;
    private TreeNode right;
    private TreeNode parent;

    public TreeNode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "value=" + value +
                ", left=" + getTreeNodeValue(left) +
                ", right=" + getTreeNodeValue(right) +
                ", parent=" + getTreeNodeValue(parent) +
                '}';
    }

    private String getTreeNodeValue(TreeNode node) {
        return node == null ? " " : node.getValue() + "";
    }

    // ======================create method =======================================

    public static TreeNode of(int value) {
        return new TreeNode(value);
    }

    public static TreeNode createBinaryTreeFromArray(int[] array) {
        TreeNode head = TreeNode.of(array[0]);
        createTree(head, array, 0);
        return head;
    }

    private static void createTree(TreeNode head, int[] srcArray, int index) {
        if (head == null || index >= srcArray.length) {
            return;
        }

        int leftIndex = 2 * index + 1;
        if (leftIndex < srcArray.length) {
            TreeNode left = TreeNode.of(srcArray[leftIndex]);
            head.setLeft(left);
            left.setParent(head);
        }
        int rightIndex = 2 * index + 2;
        if (rightIndex < srcArray.length) {
            TreeNode right = TreeNode.of(srcArray[rightIndex]);
            head.setRight(right);
            right.setParent(head);
        }

        createTree(head.getLeft(), srcArray, leftIndex);
        createTree(head.getRight(), srcArray, rightIndex);
    }
}
