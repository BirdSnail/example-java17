package birdsnail.example.算法.并查集;

import java.util.*;

import birdsnail.example.entity.DisjointNode;

public class DynamicUnionSet<E> implements IUnionSet<E> {

    private final IUnionSet<E> defaultUnionSet = new DefaultUnionSet<>(Collections.emptyList());

    public void union(E element, E expectParent) {
        if (element == null && expectParent == null) {
            return;
        }

        DisjointNode<E> nodeOne = appendIfAbsent(element);
        DisjointNode<E> nodeTwo = appendIfAbsent(expectParent);

        if (element == null || expectParent == null) {
            return;
        }

        defaultUnionSet.union(element, expectParent);
    }

    @Override
    public DisjointNode<E> appendIfAbsent(E element) {
        return defaultUnionSet.appendIfAbsent(element);
    }

    /**
     * 获取独立区域的个数
     */
    public int getIsolationNums() {
        return defaultUnionSet.getIsolationNums();
    }

    /**
     * 获取元素所属圈子的大小
     */
    public int getSize(E key) {
        return defaultUnionSet.getSize(key);
    }

    public boolean isSameSet(E one, E another) {
        return defaultUnionSet.isSameSet(one, another);
    }
}
