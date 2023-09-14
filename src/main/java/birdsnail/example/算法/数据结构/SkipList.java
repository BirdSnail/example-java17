package birdsnail.example.算法.数据结构;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳表
 */
public class SkipList {

    /*跳表允许的最高层数*/
    public static final int MAX_LEVEL = 34;

    private SkipListNode head;

    private int size;

    /**
     * 当前跳表达到的最大高度
     */
    private int level;

    public SkipList() {
        head = new SkipListNode(MAX_LEVEL);
        level = 1;
    }

    /**
     * 根据分数查询
     *
     * @param score
     * @return
     */
    public SkipListNode searchByScore(double score) {
        SkipListNode start = head;
        for (int i = level - 1; i >= 0; i--) {
            while (true) {
                SkipListLevel sl = start.getForwards().get(i);
                SkipListNode right = sl.getRight();
                if (right == null || right.getScore() > score) { // 末尾
                    break;
                }
                if (right.getScore() == score) {
                    return right;
                } else { // 查找的分值大于当前节点score，向前
                    start = sl.getRight();
                }
            }
        }
        return null;
    }

    /**
     * 添加数据
     *
     * @param data  要添加的数据
     * @param score 分值
     */
    public void add(Object data, double score) {
        SkipListLevel[] updates = findPath(score);
        insert(data, score, updates);
        size++;
    }

    private void insert(Object data, double score, SkipListLevel[] updates) {
        int insertLevel = randomLevel();
        SkipListNode newNode = new SkipListNode(data, score, insertLevel);
        for (int i = 0; i < insertLevel && i < level; i++) {
            SkipListLevel upLev = updates[i];
            // 指向后面的节点
            newNode.getForwards().get(i).setRight(upLev.getRight());
            upLev.setRight(newNode);
        }
        if (insertLevel > level) {
            for (int i = level; i < insertLevel; i++) {
                head.getForwards().get(i).setRight(newNode); // 直接指向新节点
            }
            level = insertLevel;
        }
    }

    private SkipListLevel[] findPath(double score) {
        SkipListLevel[] updates = new SkipListLevel[level];
        SkipListNode start = head;
        for (int i = level - 1; i >= 0; i--) {
            SkipListLevel sl = start.getForwards().get(i);
            while (sl.getRight() != null && sl.getRight().getScore() < score) {
                start = sl.getRight(); // 前进
                sl = start.getForwards().get(i);
            }
            updates[i] = sl;
        }
        return updates;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public void printSkipList() {
        List<SkipListLevel> forwards = head.getForwards();
        for (int i = level - 1; i >= 0; i--) {
            System.out.print("Level" + (i + 1) + "==>");
            SkipListNode node = forwards.get(i).getRight();
            while (node != null) {
                System.out.print(node);
                node = node.getForwards().get(i).getRight();
            }
            System.out.println("nil");
        }
    }

    // ========================静态类===================================

    /**
     * 跳表节点
     */
    @Data
    public static class SkipListNode {

        private Object data;

        private double score = Double.MIN_VALUE;

        private List<SkipListLevel> forwards;

        public SkipListNode(int level) {
            this.forwards = new ArrayList<>(level);
            for (int i = 0; i < level; i++) {
                this.forwards.add(new SkipListLevel());
            }
        }

        public SkipListNode(Object data, double score, int level) {
            this(level);
            this.data = data;
            this.score = score;
        }

        @Override
        public String toString() {
            return String.format("(%s, %f)-->", data.toString(), score);
        }
    }

    /**
     * 跳表索引层
     */
    @Data
    public static class SkipListLevel {
        private int span;

        private SkipListNode right;

    }

}
