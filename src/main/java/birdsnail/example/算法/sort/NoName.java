package birdsnail.example.算法.sort;

import java.util.*;

public class NoName {

    /**
     * 查询次数大于10次的数字
     */
    public static List<Integer> count(List<Set<Integer>> input) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Set<Integer> integers : input) {
            for (Integer integer : integers) {
                countMap.compute(integer, (k, v) -> {
                    if (v == null) {
                        return 1;
                    }
                    return v + 1;
                });
            }
        }

        List<Integer> res = new ArrayList<>();
        for (Integer num : countMap.keySet()) {
            Integer count = countMap.get(num);
            if (count > 10) {
                res.add(num);
            }
        }

        return res;
    }

}
