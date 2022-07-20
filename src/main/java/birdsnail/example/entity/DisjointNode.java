package birdsnail.example.entity;

/**
 * 并查集（不交集，一系列没有重复元素的集合）节点
 */
public class DisjointNode<E> {

    /**
     * 节点的element
     */
    private E element;

    public DisjointNode(E element) {
        this.element = element;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

}
