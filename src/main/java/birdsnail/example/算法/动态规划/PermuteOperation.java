package birdsnail.example.算法.动态规划;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 全排列操作
 */
public class PermuteOperation {


    /**
     * 全排列，使用一个use保存用过的元素
     */
    public static List<String> charPermute(List<String> chars) {
        List<String> result = new ArrayList<>();
        getPermute(new HashSet<>(chars.size()), "", chars, result);
        return result;
    }

    private static void getPermute(Set<Integer> use, String ans, List<String> all, List<String> result) {
        if (use.size() == all.size()) {
            result.add(ans);
            return;
        }

        for (int i = 0; i < all.size(); i++) {
            if (!use.contains(i)) {
                use.add(i);
                getPermute(use, ans + all.get(i), all, result);
                // 开启新的一轮递归遍历时要删除已经用过的，因为排列顺序发生了改变
                use.remove(i);
            }
        }
    }

}
