package birdsnail.example.base;

/**
 * @author huadong.yang
 * @date 2022-12-06 17:32
 */
public class InterviewQuestions {

    /**
     * 自动向上转型
     */
    public static void shorToIntByteCode() {
        short s1 = 1;
        // s1 = s1 + 1; // 自动转型成int，导致编译不通过
        short s2 = 1;
        s2 += 1; // 编译通过， 编译器会帮我们编译成g s2 = (short)(s2 + 1)

    }

    /**
     * Integer对象缓存, [-128~127]会走缓存
     */
    public static void integerCache() {
        Integer a = 128, b = 128, c = 127, d = 127;
        System.out.println(a == b); // false
        System.out.println(c == d); // true
    }

    public static void main(String[] args) {
        new Table();
    }

    static class Table {
        {
            System.out.println("table非静态代码快——1");
        }

        /**
         * 构造函数在对象初始化时是最后执行，非静态代码块的执行优先于构造函数
         */
        public Table() {
            System.out.println("table构造函数");
        }

        {
            System.out.println("table非静态代码快");
        }

    }

}
