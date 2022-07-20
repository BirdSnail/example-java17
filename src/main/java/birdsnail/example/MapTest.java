package birdsnail.example;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {

        var map = new HashMap<>(Map.of("k1", "value1", "key2", "value2"));

        map.compute("k1", (k, v) -> v + "_append1");
        System.out.println(map.get("k1"));

        map.computeIfAbsent("k1", k -> k + "append_2");
        System.out.println(map.get("k1"));

        // 不存在就根据key计算一个新值
        map.computeIfAbsent("k3", k -> k + "_append_3");
        System.out.println(map.get("k3"));

        // key存在映射值的话，就让已存在的映射值参与计算，生成一个新值
        map.computeIfPresent("key2", (k, v) -> v + "_key2");
        System.out.println(map.get("key2"));

    }

}
