package birdsnail.example.base;

public class CodePoint {
    public static void main(String[] args) {
        String greeting = "你好世界Hello";
        // length方法将返回采用 UTF-16 编码表示给定字符串所需要的代码单元数量
        int n = greeting.length();
        System.out.println(n);

        // 代码点（Code Point）：在 Unicode 代码空间中的一个值，取值 0x0 至 0x10FFFF，代表一个字符。

        // 代码单元（Code Unit）：在具体编码形式中的最小单位。
        // 比如 UTF-16 中一个 code unit 为 16 bits，UTF-8 中一个 code unit 为 8 bits。
        // 一个 code point 可能由一个或多个 code unit(s) 表示。
        // 在 U+10000 之前的 code point 可以由一个 UTF-16 code unit 表示，
        // U+10000 及之后的 code point 要由两个 UTF-16 code units 表示
        // 在Java中，char类型描述了UTF-16编码中的一个代码单元


        // 获得码点数量
        // String API
        // int codePointCount(int startIndex, int endIndex)
        // 返回 startIndex 和 endIndex - 1 之间的码点个数

        int cpCount = greeting.codePointCount(0, greeting.length());
        System.out.println("code point count:" + cpCount);

        // 调用 s.charAt(n) 将返回位置 n 的代码单元， n 介于 0 ~ s.length() - 1 之间
        // String API
        // char charAt(int index)
        // 返回给定位置的代码单元，除非对底层的代码单元感兴趣，否则不需要调用这个方法
        char first = greeting.charAt(0); // first is '你'
        char last = greeting.charAt(greeting.length() - 1); // last is 'o'
        System.out.println(first + "..." + last);

        // 得到第 i 个码点, 假设 i 为 3
        // String API
        // int offsetByCodePoints(int startIndex, int cpCount)
        // 返回从 startIndex 码点开始， cpCount 个码点后的码点索引
        int index = greeting.offsetByCodePoints(0, 3);
        // String API
        // int codePointAt(int index)
        // 返回从指定位置开始的码点
        int cp = greeting.codePointAt(index);
        System.out.println(index + "..." + cp);


        String sentence = "𝕆 is the set of octonions.";
        char ch = sentence.charAt(1);
        System.out.println(Integer.toHexString(ch));

        System.out.println("============================");
        // 如果想要遍历一个字符串，并且依次查看每个码点，可以使用下列语句：
        int i = 0;
        while (i < sentence.length()) {
            int cp1 = sentence.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp1)) i += 2;
            else i++;
            System.out.println(cp1);
        }
        System.out.println("------------------------------");
        int[] codePoints = sentence.codePoints().toArray();
        for (int codePoint : codePoints) {
            System.out.println(codePoint);
        }
        String str = new String(codePoints, 0, codePoints.length);
        System.out.println(str);
    }
}

