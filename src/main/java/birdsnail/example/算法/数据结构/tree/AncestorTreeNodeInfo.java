package birdsnail.example.算法.数据结构.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AncestorTreeNodeInfo {

    /**
     * 子树的公共祖先
     */
    private TreeNode ans;

    /**
     * 子树中是否发现节点one
     */
    private boolean isFindOne;

    /**
     * 子树中是否发现节点two
     */
    private boolean isFindTwo;

}
