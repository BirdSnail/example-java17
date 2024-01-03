package birdsnail.example.base;

import lombok.Data;

/**
 * 双向链表节点Node
 *
 * @param <E> 节点中元素类型泛型
 */
@Data
public class DoubleLinkNode< E> {

    private Integer key;

    E value;

    DoubleLinkNode<E> next;

    DoubleLinkNode<E> prev;

    public DoubleLinkNode() {
    }

    public DoubleLinkNode(E value) {
        this.value = value;
    }

    public DoubleLinkNode(E value, DoubleLinkNode<E> next, DoubleLinkNode<E> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}