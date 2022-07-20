package birdsnail.example.算法.链表;

import java.util.Objects;

/**
 * 链表操作
 */
public class NodeOperation {


    public static Node reversNode(Node node) {
        Node head = null;
        Node pre = null;
        while (node != null) {
            head = node;
            node = node.getNext();
            head.setNext(pre);
            pre = head;
        }

        return head;
    }

    /**
     * 判断链表是不是回文
     */
    public static boolean isPalindrome(Node head) {
        if (head == null || head.getNext() == null) {
            return true;
        }

        Node mid = midNode(head);
        Node newHead = reversNode(mid.getNext());
        mid.setNext(null);
        Node origin = newHead;
        Node start = head;

        while (newHead != null) {
            if (!Objects.equals(start.getValue(), newHead.getValue())) {
                // 反转再反转，保持链表原有的顺序
                Node srcNode = reversNode(origin);
                mid.setNext(srcNode);
                return false;
            }
            newHead = newHead.getNext();
            start = start.getNext();
        }

        // 反转再反转，保持链表原有的顺序
        Node srcNode = reversNode(origin);
        mid.setNext(srcNode);
        return true;
    }

    /**
     * 找到链表的中间节点，如果链表长度为偶数，返回的是上中点
     */
    public static Node midNode(Node head) {
        if (head == null) {
            throw new IllegalArgumentException("链表为空");
        }
        if (head.getNext() == null || head.getNext().getNext() == null) {
            return head;
        }

        Node slow = head.getNext();
        Node fast = head.getNext().getNext();
        while (fast.getNext() != null && fast.getNext().getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow;
    }

    /**
     * 将一个链表根据给定的值划分为三个区域，小于value的区域，等于value的区域，大于value的区域。然后依次连接三个区域形成一个新的链表
     *
     * @return 新的链表头节点
     */
    public static Node partition(Node head, int value) {
        Node smallHead = null;
        Node smallTail = null;
        Node equalHead = null;
        Node equalTail = null;
        Node moreHead = null;
        Node moreTail = null;
        Node start = head;

        while (start != null) {
            int curValue = start.getValue();
            if (curValue < value) {
                if (smallHead == null) {
                    smallHead = start;
                } else {
                    smallTail.setNext(start);
                }
                smallTail = start;
            } else if (curValue == value) {
                if (equalHead == null) {
                    equalHead = start;
                } else {
                    equalTail.setNext(start);
                }
                equalTail = start;
            } else {
                if (moreHead == null) {
                    moreHead = start;
                } else {
                    moreTail.setNext(start);
                }
                moreTail = start;
            }
            start = start.getNext();
        }

        // 链接三个区域
        if (smallTail != null) { // 存在小于区域的话就让小于区域的尾链接等于区域的头
            smallTail.setNext(equalHead);
            equalTail = (equalTail == null ? smallTail : equalTail);
        }
        if (equalTail != null) { // 存在等于区域的话就让等于区域的尾链接大于区域的头
            equalTail.setNext(moreHead);
        }

        return smallHead != null ? smallHead : (equalHead != null ? equalHead : moreHead);
    }

    /**
     * 以最快的时间复杂度和最小的空间复杂度复制一个带有rand节点的链表
     */
    public static Node fastCopy(Node head) {
        if (head == null) {
            throw new RuntimeException("链表为null");
        }

        Node start = head;
        // 复制链表中每一个节点 1 -> 1' -> 2 -> 2'
        while (start != null) {
            Node oldNext = start.getNext();
            Node copyNext = new Node(start.getValue());
            start.setNext(copyNext);
            copyNext.setNext(oldNext);
            start = oldNext;
        }

        Node cur = head;
        Node res = head.getNext();
        while (cur != null) {
            Node next = cur.getNext().getNext();
            Node rand = cur.getRand();
            Node copyRand = (rand == null ? null : rand.getNext());
            cur.getNext().setRand(copyRand);
            cur = next;
        }

        // 分离原始链表中加入的复制节点
        cur = head;
        while (cur != null) {
            Node srcNext = cur.getNext().getNext();
            Node copyNode = cur.getNext();
            cur.setNext(srcNext);
            copyNode.setNext(srcNext == null ? null : srcNext.getNext());
            cur = srcNext;
        }

        return res;
    }

    /**
     * 一个有环的链表，找到入环的第一个节点, 如果没有环返回null
     */
    public static Node firstRingNode(Node head) {
        if (head == null) {
            throw new RuntimeException("链表为null");
        }
        if (head.getNext() == null || head.getNext().getNext() == null) {
            return null;
        }

        Node slow = head;
        Node fast = head.getNext();
        while (slow != null && fast != null && fast.getNext() != null) {
            if (slow == fast) { // 快慢指针相遇代表有环
                fast = head;
                slow = slow.getNext();
                while (slow != fast) { // 再次相遇的点就是第一个入环的点
                    slow = slow.getNext();
                    fast = fast.getNext();
                }
                return slow;
            }
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return null;
    }

    /**
     * 两个无环链表的第一个相交节点，如果两个链表不相交，则返回null
     */
    public static Node intersectNodeNoLoop(Node one, Node two) {
        return intersectNodeNoLoop(one, two, null);
    }

    private static Node intersectNodeNoLoop(Node one, Node two, Node target) {
        if (one == null || two == null) {
            return null;
        }
        Node headOne = one;
        Node headTwo = two;
        target = (target == null ? null : target.getNext());
        int n = 0;
        while (headOne.getNext() != target) {
            headOne = headOne.getNext();
            n++;
        }
        while (headTwo.getNext() != target) {
            headTwo = headTwo.getNext();
            n--;
        }
        if (headOne != headTwo) { // 遍历到尾节点时还不相等肯定不相交
            return null;
        }

        // 将较长的链表的头节点设置为headOne，较短的节点设置为headTwo
        if (n >= 0) {
            headOne = one;
            headTwo = two;
        } else {
            headOne = two;
            headTwo = one;
        }

        n = Math.abs(n);
        while (n > 0) { // 长链表从头先走n步
            headOne = headOne.getNext();
            n--;
        }
        while (headOne != target) {
            if (headOne == headTwo) {
                return headOne;
            }
            headOne = headOne.getNext();
            headTwo = headTwo.getNext();
        }
        return null;
    }

    /**
     * 两个链表第一个相交的节点。 如果两个链表都有环并且相交，则它们必定公用环。如果不相交就返回null
     */
    public static Node intersectNodeWithLoop(Node one, Node two) {
        Node oneLoopEntryNode = firstRingNode(one);
        Node twoLoopEntryNode = firstRingNode(two);

        if (oneLoopEntryNode == null && twoLoopEntryNode == null) {
            return intersectNodeNoLoop(one, two);
        }
        // 一个有环一个无环，它们不可能相交
        if (oneLoopEntryNode == null) {
            return null;
        }
        if (twoLoopEntryNode == null) {
            return null;
        }
        // 都有环并且两个链表环的入口相同
        if (oneLoopEntryNode == twoLoopEntryNode) {
            return intersectNodeNoLoop(one, two, oneLoopEntryNode);
        }

        Node start = oneLoopEntryNode.getNext();
        while (start != oneLoopEntryNode) {
            Node next = start.getNext();
            if (next == twoLoopEntryNode) {
                return oneLoopEntryNode;
            }
            start = next;
        }

        // 两个独立的带有环的链表，不相交
        return null;
    }

    /**
     * 合并两个有序链表
     */
    public static Node mergeSortNode(Node one, Node two) {
        if (one == null) {
            return two;
        }
        if (two == null) {
            return one;
        }

        Node newHead;
        Node tail;
        if (one.getValue() <= two.getValue()) {
            newHead = one;
            one = one.getNext();
        } else {
            newHead = two;
            two = two.getNext();
        }
        tail = newHead;

        while (one != null && two != null) {
            if (one.getValue() <= two.getValue()) {
                Node next = one;
                tail.setNext(next);
                tail = next;
                one = one.getNext();
            } else {
                Node next = two;
                tail.setNext(next);
                tail = next;
                two = two.getNext();
            }
        }

        if (one != null) {
            tail.setNext(one);
        }
        if (two != null) {
            tail.setNext(two);
        }

        return newHead;
    }

    public static void print(Node node) {
        while (node != null) {
            System.out.print(node.getValue() + "->");
            node = node.getNext();
        }
    }

}
