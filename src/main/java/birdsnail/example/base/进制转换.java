package birdsnail.example.base;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 进制之间的转换看这篇帖子：<a href="https://www.zhihu.com/question/357414448/answer/949086536">点这里</a>
 *
 * <p>
 * 核心思想一个R进制数可以表述为 num = ...a*R^4 + b*R^3 + c*R^2 + d*R^1 + e*R^0 + f*R^-1 + g*R^-2 + h*R^-3 +...
 * <br>指数大于或等于0的代表整数部分，小于0的代表小数部分
 * </p>
 */
public class 进制转换 {


    /**
     * 十进制转2进制。
     * <p>
     * ...a*2^4 + b*2^3 + c*2^2 + d*2^1 + e*2^0 = 2019 <br>
     * 两边都除以2得到：...a*2^3 + b*2^2 + c*2^1 + d*2^0 + e/2 = 2019/2 = 2018/2 + 1/2 <br>
     * e/2 =  1/2 --》 e=1
     * <p>重复执行上述逻辑，...a*2^3 + b*2^2 + c*2^1 + d*2^0 = 2018/2 =1009  <br>
     * ...a*2^2 + b*2^1 + c*2^0 + d/2 = 1009/2 = 1008/2 + 1/2  <br>
     * 可得d =1
     * <p>
     * 由此可见，十进制可以不断的除以2，整数被分为两部分，商和余数，而余数就是低位的数值。总体就是余数作为二进制的低位，商作为新的被除数，
     * 不断循环，直到商为0.
     * </p>
     * </p>
     *
     * @param n 十进制数
     * @return 二进制的表示
     */
    public static String integerTrans(int n) {
        List<Integer> list = new ArrayList<>();
        while (n != 0) {
            list.add(n % 2);
            n /= 2;
        }
        list = list.reversed();
        return StringUtils.join(list, "");
    }

    /**
     * 十进制小数转换为2进制，精确10为小数
     * <p>
     *     f*2^-1 + g*2^-2 + h*2^-3... = 0.723 <br>
     *     两边乘以2得: f*2^0 + g*2^-1 + h*2^-2... =1.446  <br>
     *     f + g*2^-1 + h*2^-2... =1 +0.446 --》 f= 1
     *     重复执行上述逻辑：2*（g*2^-1 + h*2^-2 + ...） = 0.446 *2
     *     g + h*2^-1 = 0 + 0.982 --》 g=0
     * </p>
     * <p>
     *     不断执行上述乘以2的逻辑，整数部分就是二进制高位，小数部分参与下次循环，直到小数部分为0. 在计算过程中会可能会无限循环，需要指定循环次数，
     *     也就是对应的精度，这也是计算机为什么无法精确表示浮点数的原因，
     * </p>
     *
     * @param n 十进制的小数
     * @return 二进制表示
     */
    public static String decimalTrans(double n) {
        if (n > 1) {
            n = n - (int) n;
        }
        List<Integer> list = new ArrayList<>();
        int count = 10; // 精确度
        while (count > 0) {
            n = n * 2;
            list.add((int) (n));
            n = n - (int) (n);
            count--;
        }

        return StringUtils.join(list, "");
    }

    public static void main(String[] args) {
        System.out.println(integerTrans(2024));
        System.out.println(decimalTrans(0.22));
        System.out.println(decimalTrans(1.22));
    }


}
