package birdsnail.example.算法.tree;

import lombok.Data;

/**
 * 红黑树：
 * <ol>
 * <li/>每个结点是红色或者黑色的
 * <li/>根结点是黑色的
 * <li/>每个叶子结点是黑色的
 * <li/>如果一个结点是红色的，那它的两个子结点都是黑色的
 * <li/>对每个结点，从该结点到其所有后代叶子结点的简单路径上都含有相同数据的黑色结点
 * </ol>
 *
 * @author hfx
 * @since 2022-02-07 10:35
 */
public class RedBlackTree {

    private Node root;

    /**
     * 在红黑树中插入结点z
     */
    public void insert(RedBlackTree tree, Node z) {
        Node x = tree.root, y = null;
        // 在叶子结点进行插入
        while (x != null) {
            // x 最后会遍历到null 所以用y存储真正的插入位置
            y = x;
            if (z.key < x.key) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == null) {
            tree.root = z;
        } else {
            if (z.key < y.key) {
                y.left = z;
            } else {
                y.right = z;
            }
        }
        z.left = null;
        z.right = null;
        // todo 为什么新插入的结点颜色是红色
        /*
         *  若插入的结点颜色为黑色，则除了空树的情况外，
         *  一定破坏了红黑树的性质（对于每个结点，从该结点到其所有后代叶子结点的简单路径上，含有相同数量的黑色结点）
         *  因为在当前路径上增加了一颗黑色结点
         *  而插入红色结点，只有当z为根结点或者它与父结点都为红色时破坏了红黑树的结点
         * （根结点是黑色的 / 如果一个结点是红色结点，那它的子结点一定是黑色的）
         */
        z.color = Color.RED;
        // 以上是按照普通二叉树的插入方式进行插入

        // 应用 rb-insert-fixup 使得树仍为合法的红黑树
        rbInsertFixup(tree, z);

    }

    /**
     * 插入新结点后，对树进行修复，使其仍满足红黑树的性质
     */
    private void rbInsertFixup(RedBlackTree tree, Node z) {
        if (tree.root == z) {
            // z为根结点，变色 -- 根结点是黑色的
            z.color = Color.BLACK;
            return;
        }
        // z一定是红色的！
        // 只需要处理 z 和 z.parent 均为红色的情况
        Node y = null;
        while (z.parent != null && z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
            } else {
                y = z.parent.parent.left;
            }
            // case 1 ： z的叔结点是红色的
            // 变色
            if (y.color == Color.RED) {
                z.parent.color = Color.BLACK;
                y.color = Color.BLACK;
                z.parent.parent.color = Color.RED;

                z = z.parent.parent;
            }
            // case 2 : z的叔结点是黑色的且z是一个右孩子
            // 左旋 z.p
            else if (z == z.parent.right) {
                z = z.parent;
                leftRotate(tree, z);
            }
            // case 3 : z的叔结点是黑色的且z是一个左孩子
            // 右旋z 并变色
            else if (z == z.parent.left) {
                z.parent.color = Color.BLACK;
                z.parent.parent.color = Color.RED;
                rightRotate(tree, z.parent.parent);
            }
        }
        tree.root.color = Color.BLACK;
    }

    /**
     * 左旋
     */
    private void leftRotate(RedBlackTree tree, Node x) {
        Node y = x.right;

        if (y == null) {
            return;
        }

        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        y.parent = x.parent;
        if (x.parent == null) {
            tree.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;

    }

    /**
     * 右旋
     */
    private void rightRotate(RedBlackTree tree, Node x) {
        Node y = x.left;

        if (y == null) {
            return;
        }

        x.left = y.right;
        if (y.right != null)
            y.right.parent = x;

        y.parent = x.parent;
        if (x.parent == null) {
            tree.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }

    // todo
    public void delete() {

    }

    @Data
    public static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private Integer key;
        private Color color;
    }

    public enum Color {
        RED,
        BLACK;
    }
}
