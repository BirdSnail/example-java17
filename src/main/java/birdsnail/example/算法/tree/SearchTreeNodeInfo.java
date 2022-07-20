package birdsnail.example.算法.tree;


public record SearchTreeNodeInfo(
        // 最大二叉搜索树头节点
        TreeNode sbtHead,
        // 是否是搜索二叉树
        boolean isSbt,
        // 二叉搜索树最大节点树
        int maxSbtSize,
        //搜索二叉树的最小值
        int minSbtValue,
        //搜索二叉树的最大值
        int maxSbtValue) {
}
