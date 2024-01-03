package birdsnail.example.算法.常见算法;

import birdsnail.example.base.DoubleLinkNode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * hash表 + 双向链表实现
 */
public class LruCache {

    @Getter
    private int size;

    private final int capacity;

    private final DoubleLinkNode<Object> head;

    private final DoubleLinkNode<Object> tail;

    private final Map<Integer, DoubleLinkNode<Object>> keyMap;


    public LruCache(int capacity) {
        this.capacity = capacity;
        this.keyMap = new HashMap<>();

        head = new DoubleLinkNode<>();
        tail = new DoubleLinkNode<>();
        head.setNext(tail);
        tail.setPrev(head);
    }

    public void put(Integer key, Object value) {
        DoubleLinkNode<Object> exists = keyMap.get(key);
        // 不存在则创建
        if (exists == null) {
            DoubleLinkNode<Object> node = new DoubleLinkNode<>(value);
            node.setKey(key);
            keyMap.put(key, node);
            this.size++;
            addToHead(node);

            if (size > capacity) {
                removeTail();
            }
        }
        // 更新值
        else {
            exists.setValue(value);
            moveToHead(exists);
        }
    }

    public Object get(Integer key) {
        DoubleLinkNode<Object> node = keyMap.get(key);
        if (node == null) {
            return null;
        }
        // 移动到链表头部
        moveToHead(node);
        return node.getValue();
    }

    public Object getFirst() {
        DoubleLinkNode<Object> next = head.getNext();
        if (next == null) {
            return null;
        }
        return next.getValue();
    }

    public Object getLast() {
        DoubleLinkNode<Object> prev = tail.getPrev();
        if (prev == null) {
            return null;
        }
        return prev.getValue();
    }

    private void addToHead(DoubleLinkNode<Object> node) {
        DoubleLinkNode<Object> headNext = head.getNext();
        head.setNext(node);
        node.setNext(headNext);
        node.setPrev(head);
        headNext.setPrev(node);
    }

    private void removeTail() {
        while (size > capacity) {
            DoubleLinkNode<Object> prev = tail.getPrev();
            if (prev == head) {
                return;
            }
            remove(prev);
        }
    }

    private void remove(DoubleLinkNode<Object> node) {
        DoubleLinkNode<Object> prev = node.getPrev();
        DoubleLinkNode<Object> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);
        node.setNext(null);
        node.setPrev(null);

        Integer key = node.getKey();
        keyMap.remove(key);
        this.size--;
    }

    private void moveToHead(DoubleLinkNode<Object> node) {
        DoubleLinkNode<Object> prev = node.getPrev();
        DoubleLinkNode<Object> next = node.getNext();
        prev.setNext(next);
        next.setPrev(prev);

        addToHead(node);
    }


}
