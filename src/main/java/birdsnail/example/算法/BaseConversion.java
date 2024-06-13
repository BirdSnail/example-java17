package birdsnail.example.算法;

import java.util.HashMap;
import java.util.Map;

/**
 * 进制转换
 */
public class BaseConversion {

    private final static int BASE_16 = 16;
    private static Map<Character, Integer> map = new HashMap<>();

    static {
        map.put('0', 0);
        map.put('1', 1);
        map.put('2', 2);
        map.put('3', 3);
        map.put('4', 4);
        map.put('5', 5);
        map.put('6', 6);
        map.put('7', 7);
        map.put('8', 8);
        map.put('9', 9);
        map.put('A', 10);
        map.put('B', 11);
        map.put('C', 12);
        map.put('D', 13);
        map.put('E', 14);
        map.put('F', 15);
        map.put('a', 10);
        map.put('b', 11);
        map.put('c', 12);
        map.put('d', 13);
        map.put('e', 14);
        map.put('f', 15);
    }

    /**
     * 16进制转换为10进制. num * base(index)幂
     */
    public static int hexToNum(String num) {
        char[] charArray = num.toCharArray();
        int result = 0;
        for (int i = charArray.length - 1; i >= 0; i--) {
            int n = charArray.length - 1 - i;
            double temp = map.get(charArray[i]) * Math.pow(BASE_16, n);
            result = result + (int) temp;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(hexToNum("AA"));
        System.out.println(hexToNum("10"));
    }

}
