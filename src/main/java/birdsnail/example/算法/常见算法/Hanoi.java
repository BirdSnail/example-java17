package birdsnail.example.算法.常见算法;

/**
 * 汉诺塔问题
 */
public class Hanoi {

    /**
     * 第一种解法，利用大量的小方法，十分便于理解
     */
    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.printf("move %s from left to right%n", n);
        midToRight(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to right");
            return;
        }

        midToLeft(n - 1);
        System.out.printf("move %s from mid to right%n", n);
        leftToRight(n -1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to left");
            return;
        }

        midToRight(n - 1);
        System.out.printf("move %s from mid to right%n", n);
        rightToLeft(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to left");
            return;
        }

        rightToMid(n - 1);
        System.out.printf("move %s from right to left%n", n);
        leftToMid(n -1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to mid");
            return;
        }

        rightToLeft(n - 1);
        System.out.printf("move %s from right to mid%n", n);
        leftToMid(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to mid");
            return;
        }

        leftToRight(n - 1);
        System.out.printf("move %s from left to mid%n", n);
        rightToMid(n - 1);
    }


    /**
     * 第二种解法，抽象出from， to， other
     *
     * @param n 汉诺塔层数
     */
    public static void resolution2(int n) {
        move(n, "left", "mid", "right");
    }

    private static void move(int n, String from, String other, String to) {
        if (n == 1) {
            System.out.printf("move 1 from %s to %s%n", from, to);
            return;
        }
        move(n -1, from, to, other); // n-1个盘移到other
        System.out.printf("move %d from %s to %s%n", n, from, to); // 最下面的盘移到to
        move(n - 1, other, from, to); // 把other上的盘移到to
    }

    public static void main(String[] args) {
        leftToRight(3);
        System.out.println("======");
        resolution2(3);
    }


}
