package birdsnail.example.算法.并查集;

import birdsnail.example.entity.DisjointNode;

/**
 * 并查集接口
 */
public interface IUnionSet<E> {

    /**
     * 两个元素是否属入同一个集合圈
     */
    boolean isSameSet(E e1, E e2);

    /**
     * 两个元素所在的集合圈进行合并
     */
    void union(E e1, E e2);

    /**
     * 获取独立区域的个数
     */
    int getIsolationNums();

    /**
     * 获取元素所属圈子的大小
     */
    int getSize(E key);

    /**
     * 如果所有的圈子没有这个元素，就添加一个独立的元素，此元素属入一个新的集合
     */
    DisjointNode<E> appendIfAbsent(E element);
}
