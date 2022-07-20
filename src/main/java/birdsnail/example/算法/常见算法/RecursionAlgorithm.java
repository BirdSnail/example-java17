package birdsnail.example.算法.常见算法;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * 递归算法
 */
public class RecursionAlgorithm {

    /**
     * 不适用额外的数据结构，只允许使用递归，将一个栈进行逆序操作.
     * <p>
     * 思路：递归抽出栈底元素，再入栈
     * </p>
     */
    public static <T> void reverse(Deque<T> stack) {
        if (stack.isEmpty()) {
            return;
        }
        T tail = tail(stack);
        reverse(stack);
        stack.push(tail); // 让栈底的值最后入栈，
    }

    /**
     * 移除栈的栈底元素，栈的整体顺序不变
     *
     * @return 栈底元素
     */
    private static <T> T tail(Deque<T> stack) {
        T cur = stack.pop();
        if (stack.isEmpty()) {
            return cur;
        }
        T tail = tail(stack);
        stack.push(cur); // 拿到栈底元素后重新入栈，保持栈的顺序
        return tail;
    }


    /**
     * n皇后问题，在一个n*n的棋盘上，排放n个皇后，要求皇后之间不能互相攻击（任意两个皇后不在同一行，同一列，同一斜线），求有多少种排放方法？
     *
     * @param n 皇后数量
     * @return 解法数量
     */
    public static int nQueensProblem(int n) {
        if (n == 1) {
            return 1;
        }
        int[] position = new int[n];
        return processQueen(position, 0);
    }

    /**
     * 暴力递归遍历，使用position保存每一行的皇后排放位置，position[2] = 5, 第3行的皇后放在第6列上
     *
     * @param position 存放皇后位置的数组，下标是棋盘行数
     * @param row      来到了期盼的第row行
     * @return 来到row行有多少种解法
     */
    private static int processQueen(int[] position, int row) {
        if (row == position.length) {
            return 1; // 成功走完了棋盘最后一行，意味着找到了一种排放方法
        }

        int result = 0;
        // 从第0列开始尝试
        for (int col = 0; col < position.length; col++) {
            if (isValid(position, row, col)) {
                position[row] = col;
                result += processQueen(position, row + 1);
            }
        }

        return result;
    }

    private static boolean isValid(int[] position, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (position[i] == col || Math.abs(row - i) == Math.abs(col - position[i])) { // 行之间的差距等于列之间的差距，是个等腰三角行，说明在同一斜线上（左斜线或者右斜线）
                return false;
            }
        }
        return true;
    }

    /**
     * n皇后问题的解的排法打印
     */
    public static void nQueensProblem2(int n) {
        int[] position = new int[n];
        List<List<Integer>> ans = new ArrayList<>();
        processQueen2(position, 0, ans);
        System.out.println("找到了解法数量：" + ans.size());
        print(ans);
    }

    private static void processQueen2(int[] position, int row, List<List<Integer>> collector) {
        if (row == position.length) {
            collector.add(Arrays.stream(position).boxed().toList());
            return;
        }

        // 从第0列开始尝试
        for (int col = 0; col < position.length; col++) {
            if (isValid(position, row, col)) {
                position[row] = col;
                processQueen2(position, row + 1, collector);
            }
        }
    }

    private static void print(List<List<Integer>> result) {
        for (int i = 0; i < result.size(); i++) {
            List<Integer> solves = result.get(i);
            System.out.println("==========");
            printPosition(solves);
            if (i >= 5) { // 最多打印5个解
                break;
            }
        }
    }

    private static void printPosition(List<Integer> solves) {
        for (Integer pos : solves) {
            System.out.println(Arrays.toString(formatLine(pos, solves.size()).toArray()));
        }
    }

    private static List<String> formatLine(int position, int size) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (position == i) {
                res.add("X");
            } else {
                res.add("_");
            }
        }
        return res;
    }

    public static void main(String[] args) {
        nQueensProblem2(8);
    }

}
