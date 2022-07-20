package birdsnail.example.算法.并查集;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import birdsnail.example.entity.DisjointNode;

/**
 * 并查集  时间复杂度平均下来为O(1)
 */
public class DefaultUnionSet<E> implements IUnionSet<E> {

    private final Map<E, DisjointNode<E>> nodesMap = new HashMap<>();

    /**
     * 存储父节点包含的子节点个数
     */
    private final Map<DisjointNode<E>, Integer> sameSetSizeMap = new HashMap<>();

    private final Map<DisjointNode<E>, DisjointNode<E>> parentMap = new HashMap<>();

    public DefaultUnionSet(List<E> elements) {
        for (E element : elements) {
            DisjointNode<E> node = new DisjointNode<>(element);
            nodesMap.put(element, node);
            parentMap.put(node, node);
            sameSetSizeMap.put(node, 1);
        }

    }

    /**
     * 两个元素是否属入同一个集合中
     */
    @Override
    public boolean isSameSet(E e1, E e2) {
        DisjointNode<E> oneNode = nodesMap.get(e1);
        DisjointNode<E> anoNode = nodesMap.get(e2);

        if (oneNode == null || anoNode == null) {
            return false;
        }

        return findRootParent(e1) == findRootParent(e2);
    }

    /**
     * 合并两个元素对应的集合
     */
    @Override
    public void union(E e1, E e2) {

        DisjointNode<E> firstParent = findRootParent(e1);
        DisjointNode<E> secondParent = findRootParent(e2);

        if (firstParent == null || secondParent == null) {
            return;
        }

        if (firstParent != secondParent) {
            // 小的集合圈挂到大的集合里面去
            if (sameSetSizeMap.getOrDefault(firstParent, 0) >= sameSetSizeMap.getOrDefault(secondParent, 0)) {
                resetParent(firstParent, secondParent);
            } else {
                resetParent(secondParent, firstParent);
            }
        }

    }

    private DisjointNode<E> findRootParent(E cur) {
        DisjointNode<E> curNode = nodesMap.get(cur);
        Deque<DisjointNode<E>> stack = new ArrayDeque<>();
        while (parentMap.get(curNode) != curNode) {
            stack.push(curNode);
            curNode = parentMap.get(curNode);
        }

        // 扁平化，一个父节点下面有多个子节点
        while (!stack.isEmpty()) {
            DisjointNode<E> node = stack.pop();
            parentMap.put(node, curNode);
        }
        return curNode;
    }

    /**
     * 小的圈挂到大的圈上面，重新设置大圈的size
     */
    private void resetParent(DisjointNode<E> smallParent, DisjointNode<E> bigParent) {
        parentMap.put(smallParent, bigParent);
        Integer smallSize = sameSetSizeMap.get(smallParent);
        sameSetSizeMap.computeIfPresent(bigParent, (s, b) -> b + smallSize);
        sameSetSizeMap.remove(smallParent);
    }

    /**
     * 获取独立区域的个数
     */
    @Override
    public int getIsolationNums() {
        return sameSetSizeMap.size();
    }

    /**
     * 获取元素所属圈子的大小
     */
    @Override
    public int getSize(E key) {
        DisjointNode<E> node = nodesMap.get(key);
        if (node == null) {
            return 0;
        }
        DisjointNode<E> parent = findRootParent(key);
        return sameSetSizeMap.getOrDefault(parent, 0);
    }

    @Override
    public  DisjointNode<E> appendIfAbsent(E element) {
        if (element == null) {
            return null;
        }

        if (!nodesMap.containsKey(element)) {
            DisjointNode<E> node = new DisjointNode<>(element);
            nodesMap.put(element, node);
            sameSetSizeMap.put(node, 1);
            parentMap.put(node, node);
            return node;
        }
        return nodesMap.get(element);
    }

    /**
     * 含头不含尾
     */
    public static DefaultUnionSet<Integer> ofIntRange(int start, int end) {
        List<Integer> nums = IntStream.range(start, end).boxed().collect(Collectors.toList());
        return new DefaultUnionSet<>(nums);
    }

}