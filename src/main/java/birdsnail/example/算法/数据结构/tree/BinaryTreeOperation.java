package birdsnail.example.算法.数据结构.tree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeOperation {

    /**
     * 一个二叉树的最大宽度，前提是这个二叉树与满二叉树结构类型，但是允许一些节点为null
     */
    public static int widthOfBinaryTree(TreeNode head) {
        if (head == null) {
            return 0;
        }

        TreeNode curLevelTail = head;
        TreeNode nextLevelTail = null;
        Queue<TreeNode> treeNodes = new ArrayDeque<>();
        treeNodes.add(head);

        int max = 0;
        int curLevelCount = 0;
        while (!treeNodes.isEmpty()) {
            TreeNode curNode = treeNodes.poll();
            curLevelCount++;
            if (curNode.getLeft() != null) {
                treeNodes.add(curNode.getLeft());
                nextLevelTail = curNode.getLeft();
            }
            if (curNode.getRight() != null) {
                treeNodes.add(curNode.getRight());
                nextLevelTail = curNode.getRight();
            }

            // 当前节点是这一层最后的节点
            if (curNode == curLevelTail) {
                max = Math.max(max, curLevelCount);
                curLevelCount = 0; // 要开启新的一层，新的一层重新计数
                curLevelTail = nextLevelTail;
            }
        }

        return Math.max(max, curLevelCount);
    }

    /**
     * 节点有一个parent指针指向父节点。给点任意一个节点，返回按照中序遍历时该节点的下一个节点(后继节点)
     * <pre>
     *       1
     *      / \
     *     2   3
     *    / \
     *   4   5
     * </pre>
     * 给定2节点，返回5.因为中序遍历是【4，2，5，1，3】
     */
    public static TreeNode getSuccessorNode(TreeNode node) {
        if (node == null) {
            return null;
        }

        // 按照中序遍历的方式 左中右， 当前节点子树的最左节点
        if (node.getRight() != null) {
            return getLeftMost(node.getRight());
        }

        // 向上遍历，直到当前节点是父节点的左节点
        while (true) {
            TreeNode parent = node.getParent();
            if (parent == null) { // 头节点没有父节点
                return null;
            }
            if (parent.getLeft() == node) {
                return parent;
            }
            node = parent;
        }
    }

    private static TreeNode getLeftMost(TreeNode head) {
        if (head == null) {
            return null;
        }

        while (head.getLeft() != null) {
            head = head.getLeft();
        }
        return head;
    }

    /**
     * 给定一个树的头节点，判断是不是平衡二叉树
     * <p>平衡二叉树的性质：
     * <ul>
     *     <li>左子树是个平衡二叉树</li>
     *      <li>右子树是个平衡二叉树</li>
     *   <li>左子树与右子树的高度相差不超过一</li>
     *   <li>空树是平衡二叉树</li>
     * </ul></p>
     */
    public static boolean isBalanceTree(TreeNode head) {
        TreeNodeInfo treeNodeInfo = getTreeNodeInfo(head);
        return treeNodeInfo.isBalance();
    }

    private static TreeNodeInfo getTreeNodeInfo(TreeNode treeNode) {
        if (treeNode == null) {
            return new TreeNodeInfo(0, true);
        }

        TreeNodeInfo leftTreeNodeInfo = getTreeNodeInfo(treeNode.getLeft());
        TreeNodeInfo rightTreeNodeInfo = getTreeNodeInfo(treeNode.getRight());
        // 左右子树都平衡并且高度差不超过一
        boolean isBalanceCurrent = leftTreeNodeInfo.isBalance() && rightTreeNodeInfo.isBalance()
                && Math.abs(leftTreeNodeInfo.high() - rightTreeNodeInfo.high()) <= 1;
        // 当前树的高度
        int highCurrent = Math.max(leftTreeNodeInfo.high(), rightTreeNodeInfo.high()) + 1;
        return new TreeNodeInfo(highCurrent, isBalanceCurrent);
    }


    /**
     * 在一个树中找出子树为最大搜索二叉树的头节点
     */
    public static TreeNode findSearchBinaryTreeHead(TreeNode head) {
        SearchTreeNodeInfo searchTreeNodeInfo = getSearchBinaryTreeHead(head);
        return searchTreeNodeInfo.sbtHead();
    }

    public static SearchTreeNodeInfo getSearchBinaryTreeHead(TreeNode head) {
        if (head == null) { // 空树是搜索二叉树
            return new SearchTreeNodeInfo(null, true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }

        SearchTreeNodeInfo leftSbtInfo = getSearchBinaryTreeHead(head.getLeft());
        SearchTreeNodeInfo rightSbtInfo = getSearchBinaryTreeHead(head.getRight());

        // 二叉搜索子树的最小值和最大值
        int minSbtValue = leftSbtInfo.minSbtValue();
        int maxSbtValue = rightSbtInfo.maxSbtValue();

        if (leftSbtInfo.isSbt() && rightSbtInfo.isSbt()
                // 左子树最大值依然小于当前节点值 且 右子树最小值依然大于当前节点值，可以认为当前节点还是二叉搜索树
                && leftSbtInfo.maxSbtValue() < head.getValue() && rightSbtInfo.minSbtValue() > head.getValue()) {
            int maxSize = leftSbtInfo.maxSbtSize() + rightSbtInfo.maxSbtSize() + 1;
            if (minSbtValue == Integer.MAX_VALUE) {
                minSbtValue = head.getValue();
            }
            if (maxSbtValue == Integer.MIN_VALUE) {
                maxSbtValue = head.getValue();
            }
            return new SearchTreeNodeInfo(head, true, maxSize, minSbtValue, maxSbtValue);
        }

        // 走到以下代码意味着当前节点就不是二叉搜索树了, 取左子树或者右子树的相关信息
        if (leftSbtInfo.maxSbtSize() >= rightSbtInfo.maxSbtSize()) {
            return new SearchTreeNodeInfo(leftSbtInfo.sbtHead(), false, leftSbtInfo.maxSbtSize(), leftSbtInfo.minSbtValue(), leftSbtInfo.maxSbtValue());
        }
        return new SearchTreeNodeInfo(rightSbtInfo.sbtHead(), false, rightSbtInfo.maxSbtSize(), rightSbtInfo.minSbtValue(), rightSbtInfo.maxSbtValue());
    }

    /**
     * 是否是完全二叉树
     * 使用广度优先遍历
     */
    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        boolean reachEnd = false;
        while (!nodeQueue.isEmpty()) {
            TreeNode treeNode = nodeQueue.poll();
            if (reachEnd && treeNode != null) {
                return false;
            }
            // 第一个遇到null的节点代表是树的终点
            if (treeNode == null) {
                reachEnd = true;
                continue;
            }

            nodeQueue.add(treeNode.getLeft());
            nodeQueue.add(treeNode.getRight());
        }

        return true;
    }

    /**
     * 寻找二叉树中任意两个节点最近的公共祖先
     */
    public static TreeNode lowestCommonAncestor(TreeNode head, TreeNode one, TreeNode two) {
        if (one == null || two == null) {
            throw new IllegalArgumentException("属入节点为null");
        }
        AncestorTreeNodeInfo lowestCommonAncestor = getLowestCommonAncestor(head, one, two);
        return lowestCommonAncestor.getAns();
    }

    public static AncestorTreeNodeInfo getLowestCommonAncestor(TreeNode head, TreeNode one, TreeNode two) {
        if (head == null) {
            return new AncestorTreeNodeInfo(null, false, false);
        }

        var leftAns = getLowestCommonAncestor(head.getLeft(), one, two);
        var rightAns = getLowestCommonAncestor(head.getRight(), one, two);

        // 在子树中已经发现了最近的公共祖先，直接返回即可
        if (leftAns.getAns() != null) {
            return leftAns;
        }
        if (rightAns.getAns() != null) {
            return rightAns;
        }

        // 在子树中是否找到了给定的节点
        boolean isFindOne = head == one || leftAns.isFindOne() || rightAns.isFindOne();
        boolean isFindTwo = head == two || leftAns.isFindTwo() || rightAns.isFindTwo();

        if (isFindOne && isFindTwo) { // 两个节点都在当前子树中，意味着当前节点就是最近的公共祖先
            return new AncestorTreeNodeInfo(head, true, true);
        }

        // 两个节点不都在当前子树中，因此当前子树不存在公共祖先
        return new AncestorTreeNodeInfo(null, isFindOne, isFindTwo);
    }

}
