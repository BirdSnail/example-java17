package birdsnail.example.算法.链表;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static birdsnail.example.算法.链表.Node.createNodeFromArray;
import static birdsnail.example.算法.链表.Node.populateNodeRand;
import static org.junit.jupiter.api.Assertions.*;

class NodeOperationTest {

    Node first;

    @BeforeEach
    void setUp() {
        first = new Node(1);
        Node second = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);

        first.setNext(second);
        second.setNext(three);
        three.setNext(four);
    }

    @Test
    void reversNode() {
        Node head = NodeOperation.reversNode(first);
        assertEquals(4, head.getValue());
        Node three = head.getNext();
        assertEquals(3, three.getValue());
        Node second = three.getNext();
        assertEquals(2, second.getValue());
        Node first = second.getNext();
        assertEquals(1, first.getValue());
        assertNull(first.getNext());
    }

    @Test
    void midNodeTest() {
        Node mid = NodeOperation.midNode(first);
        assertSame(mid, first.getNext());
    }

    @Test
    void isPalindromeTest() {
        assertFalse(NodeOperation.isPalindrome(first));
        Node one = new Node(1);
        Node second = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        Node.getTail(first).setNext(three);
        three.setNext(second);
        second.setNext(one);
        assertTrue(NodeOperation.isPalindrome(first));
    }

    @Test
    void partitionTest() {
        Node head = createNodeFromArray(new int[]{1, 5, 9, 87, 23, 10, 5, 24});
        Node first = NodeOperation.partition(head, 5);
        while (first != null) {
            System.out.print(first.getValue() + "->");
            first = first.getNext();
        }
    }

    @Test
    void fastCopyTest() {
        int[] srcArray = {1, 5, 9, 87, 23, 10, 5, 24};
        Node head = createNodeFromArray(srcArray);
        populateNodeRand(head, srcArray);

        Node copy = NodeOperation.fastCopy(head);
        while (head != null) {
            assertEquals(head.getValue(), copy.getValue());
            Node rand = head.getRand();
            Node copyRand = copy.getRand();
            if (rand == null) {
                assertNull(copyRand);
            } else {
                assertEquals(rand.getValue(), copyRand.getValue());
            }

            head = head.getNext();
            copy = copy.getNext();
        }
    }

    @Test
    void firstRingNodeTest() {
        int[] srcArray = {1, 5, 9, 87, 23, 10, 5, 24};
        Node head = createNodeFromArray(srcArray);
        assertNull(NodeOperation.firstRingNode(head));

        int loopEntryIndex = 4;
        Node loopNode = Node.createNodeWithLoopFromArray(srcArray, loopEntryIndex);
        assertEquals(1, loopNode.getValue());
        testLoopNode(loopNode, srcArray, loopEntryIndex);
    }

    private void testLoopNode(Node loopNode, int[] srcArray, int loopEntry) {
        Node firstRingNode = NodeOperation.firstRingNode(loopNode);
        assertNotNull(firstRingNode);
        assertEquals(srcArray[loopEntry], firstRingNode.getValue());
        System.out.println(firstRingNode.getValue());
    }

    @Test
    void intersectNodeNoLoopTest() {
        int[] srcArray = {1, 5, 9, 87, 23, 10, 5, 24};
        Node head = createNodeFromArray(srcArray);

        int[] array = {25, 36, 98, 8, 57};
        Node two = createNodeFromArray(array);
        Node nodeNoLoop = NodeOperation.intersectNodeNoLoop(head, two);
        assertNull(nodeNoLoop);

        Node tail = Node.getTail(two);
        tail.setNext(head.getNext().getNext().getNext());
        nodeNoLoop = NodeOperation.intersectNodeNoLoop(head, two);
        assertNotNull(nodeNoLoop);
        assertEquals(87, nodeNoLoop.getValue());

        Node headTail = Node.getTail(head);
        tail.setNext(headTail);
        nodeNoLoop = NodeOperation.intersectNodeNoLoop(head, two);
        assertNotNull(nodeNoLoop);
        assertEquals(headTail.getValue(), nodeNoLoop.getValue());
        System.out.println(headTail);
    }

    @Test
    void intersectNodeWithLoopTest() {
        // 两个无环链表
        int[] srcArray = {1, 5, 9, 87, 23, 10, 5, 24};
        Node head = createNodeFromArray(srcArray);

        int[] array = {25, 36, 98, 8, 57};
        Node two = createNodeFromArray(array);
        Node resultNode = NodeOperation.intersectNodeWithLoop(head, two);
        assertNull(resultNode);

        // 一个有环 一个无环
        Node nodeLoop = Node.createNodeWithLoopFromArray(srcArray, 4);
        resultNode = NodeOperation.intersectNodeWithLoop(head, nodeLoop);
        assertNull(resultNode);

        // 两个都有环 相交
        Node tail = Node.getTail(two);
        System.out.println("tail: " + tail);
        Node loopEntryNode = NodeOperation.firstRingNode(nodeLoop);
        assertNotNull(loopEntryNode);
        tail.setNext(loopEntryNode); // 入环入口相同
        Node intersectNode = NodeOperation.intersectNodeWithLoop(nodeLoop, two);
        assertNotNull(intersectNode);
        assertEquals(loopEntryNode.getValue(), intersectNode.getValue());
        assertSame(loopEntryNode, intersectNode);

        tail.setNext(loopEntryNode.getNext().getNext()); // 入环入口不同
        intersectNode = NodeOperation.intersectNodeWithLoop(nodeLoop, two);
        assertNotNull(intersectNode);
        assertEquals(loopEntryNode.getValue(), intersectNode.getValue());
        assertSame(loopEntryNode, intersectNode);

        Node ringNode = Node.createNodeWithLoopFromArray(new int[]{1, 3, 2}, 0);
        resultNode = NodeOperation.intersectNodeWithLoop(ringNode, ringNode);
        System.out.println("ringNode: " + ringNode);
        assertNotNull(resultNode);
        assertSame(ringNode, resultNode);

        // 两个都有环 不相交
        Node twoLoopNode = Node.createNodeWithLoopFromArray(array, 2);
        testLoopNode(twoLoopNode, array, 2);
        resultNode = NodeOperation.intersectNodeWithLoop(nodeLoop, twoLoopNode);
        assertNull(resultNode);

    }

    @Test
    void mergeSortNodeTest() {
        int[] srcArray = {1, 5, 9, 87};
        int[] array = {25, 36, 98};
        Node head = createNodeFromArray(srcArray);
        Node two = createNodeFromArray(array);

        Node sortNode = NodeOperation.mergeSortNode(head, two);
        assertSame(head, sortNode);
        assertSame(Node.getTail(sortNode), Node.getTail(two));
        NodeOperation.print(sortNode);
    }
}