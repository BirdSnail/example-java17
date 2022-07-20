package birdsnail.example.算法.动态规划;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 现有一个目标字符，如”abbcda“。有不同类型的卡片，每个卡片上有都是小写字母组成的字符，选择这些卡片，让他们组成的字符可以拼接成目标字符串。<br/>
 * 1. 每种卡片可以选择无限多张<br/>
 * 2. 卡片上的字符可以单独剪开然后重新组合。<br/>
 * <p></p>
 * 问最少需要选择多少张卡片可以组成目标字符。
 */
public class CardString {


    /**
     * 由于卡片上的字符可以被拆开随意组合，因此不用考虑顺序。
     * 暴力递归的方式穷举所属有的可能,
     *
     * @param target 目标字符
     * @param cards  卡片字符
     * @return 最少的卡片数
     */
    public static int solution(String target, List<String> cards) {
        if (target.isEmpty()) {
            return 0;
        }

        int result = Integer.MAX_VALUE;

        for (String card : cards) {
            // 选择了一张卡片
            String rest = minus(target, card);
            if (target.length() != rest.length()) {
                result = Math.min(result, solution(rest, cards));// 选择当前的卡片后，剩下的目标字符返回的结果
            }
        }

        // Integer.MAX_VALUE意味着从给定的卡片中不能找到可以组成剩下目标字符的解
        // 要加上当前卡片，因为result的结果是排除了一张选择的卡片上的字符后得到的结果
        return result + (result == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 使用缓存
     *
     * @param target 目标字符
     * @param cards  卡片字符
     * @return 最少的卡片数
     */
    public static int solution2(String target, List<String> cards) {
        return process(target, cards, new HashMap<>());
    }

    /**
     * 由于卡片上的字符可以被拆开随意组合，因此不用考虑顺序。
     * 暴力递归的方式穷举所属有的可能,
     *
     * @param target 目标字符
     * @param cards  卡片字符
     * @return 最少的卡片数
     */
    public static int process(String target, List<String> cards, Map<String, Integer> cache) {
        if (target.isEmpty()) {
            return 0;
        }

        int result = Integer.MAX_VALUE;

        for (String card : cards) {
            // 选择了一张卡片
            String rest = minus(target, card);
            if (target.length() != rest.length()) {
                Integer exists = cache.get(rest);
                // 选择当前的卡片后，剩下的目标字符返回的结果
                result = Math.min(result, Objects.requireNonNullElseGet(exists, () -> solution(rest, cards)));
            }
        }

        // Integer.MAX_VALUE意味着从给定的卡片中不能找到可以组成剩下目标字符的解
        // 要加上当前卡片，因为result的结果是确定了一张选择的卡片上的字符后得到的结果
        result = (result + (result == Integer.MAX_VALUE ? 0 : 1));
        cache.put(target, result);
        return result;
    }

    private static String minus(String target, String card) {
        List<Character> targetChars = new ArrayList<>(target.chars().mapToObj(c -> (char) c).toList());
        List<Character> cardChars = card.chars().mapToObj(c -> (char) c).toList();
        for (Character cardChar : cardChars) {
            targetChars.remove(cardChar);
        }
        return targetChars.stream().map(String::valueOf).sorted().collect(Collectors.joining());
    }


    /**
     * 不考虑顺序的字符串可以转换为数组的表示方法，数组的index代表这具体字母，0-->a,1-->b....25-->z。index处的值为该字母出现的次数。<br/>
     * [0,1,1,2]-->bcdd
     * <p></p>
     * 没选择一个card时，target转换成的数组词频减去card的词频，当所有词频变成0时，意味着该target被成功拼成
     */
    public static int solution3(String target, List<String> cards) {
        int[][] cardArr = new int[cards.size()][26];
        for (int i = 0; i < cards.size(); i++) {
            char[] cardChar = cards.get(i).toCharArray();
            for (char c : cardChar) {
                cardArr[i][c - 'a']++;
            }
        }

        int[] targetArr = new int[26];
        for (char ch : target.toCharArray()) {
            targetArr[ch - 'a']++;
        }
        return process3(cardArr, targetArr);
    }

    private static int process3(int[][] cardArr, int[] targetArr) {
        int sum = Arrays.stream(targetArr).sum();
        if (sum <= 0) {
            return 0;
        }

        int result = Integer.MAX_VALUE;

        /*
         * 按照字母顺序将我们的target所有的字符词频都减为0，如果一个卡片不包含需要的字母，可以跳过该卡片。
         * 如aabbbccd:先找出包含a的卡片，让a的词频减少到0，再找出包含b的卡片，让b的词频减少到0，依次类推
         */
        for (int[] card : cardArr) {
            int first = getFirstChar(targetArr);
            int[] temp = Arrays.copyOf(targetArr, targetArr.length);
            // 剪枝，只考虑包含当前字符的卡片
            if (card[first] > 0) {
                for (int j = 0; j < card.length; j++) {
                    temp[j] -= card[j];// 移除卡片上的字母
                    if (temp[j] < 0) {
                        temp[j] = 0;
                    }
                }
                result = Math.min(result, process3(cardArr, temp));// 移除完成后进入下一轮
            }
        }

        return result + (result == Integer.MAX_VALUE ? 0 : 1);
    }

    // 获取目标字符串的第一个字符
    private static int getFirstChar(int[] targetArr) {
        for (int i = 0; i < targetArr.length; i++) {
            if (targetArr[i] > 0) {// 词频大于0就代表有该字符，字符就是index
                return i;
            }
        }
        throw new RuntimeException();
    }

}
