package birdsnail.example.算法.链表;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Node {

    private final int value;
    private Node next;
    private Node rand;


    public Node(int value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getValue() {
        return value;
    }

    public Node getRand() {
        return rand;
    }

    public void setRand(Node rand) {
        this.rand = rand;
    }

    public Node getNext() {
        return next;
    }

    public static Node getTail(Node head) {
        if (head == null) {
            throw new IllegalArgumentException("链表为null");
        }

        Node start = head;
        while (start.getNext() != null) {
            start = start.getNext();
        }
        return start;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", next=" + (next == null ? "null" : next.getValue()) +
                '}';
    }


    // ============================create method=============================================

    public static Node of(int value) {
        return new Node(value);
    }

    public static Node createNodeFromArray(int[] array) {
        Node first = null;
        Node next = null;
        for (int i : array) {
            if (first == null) {
                first = new Node(i);
                next = first;
            } else {
                next.setNext(new Node(i));
                next = next.getNext();
            }
        }
        return first;
    }

    /**
     * 创建一个带有环的链表
     *
     * @param array     数组
     * @param loopEntry 指定该位置的节点为环的入口节点
     */
    public static Node createNodeWithLoopFromArray(int[] array, int loopEntry) {
        if (loopEntry >= array.length - 1) {
            throw new IllegalArgumentException("环的入口位置超过了给定的数组长度");
        }

        List<Node> nodeList = Arrays.stream(array).mapToObj(Node::of).collect(Collectors.toList());

        int index = 0;
        int tail = nodeList.size() - 1;
        while (index < tail) {
            nodeList.get(index++).setNext(nodeList.get(index));
        }
        Node loopEntryNode = nodeList.get(loopEntry);
        nodeList.get(tail).setNext(loopEntryNode);
        return nodeList.get(0);
    }


    public static void populateNodeRand(Node head, int[] srcArray) {
        int size = srcArray.length;
        List<Node> nodeList = new ArrayList<>();
        while (head != null) {
            nodeList.add(head);
            head = head.getNext();
        }

        Random random = new Random();
        for (int index = 0; index < size; index++) {
            int randIndex = random.nextInt(size + 3);
            Node rand = null;
            if (randIndex < size) {
                rand = nodeList.get(randIndex);
            }
            nodeList.get(index).setRand(rand);
        }

    }
}
