package birdsnail.example.quicktest;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 解析并按照不同统计指标排名
 */
public class JsonTest {


    public static void main(String[] args) throws IOException {

        String json = Files.readString(Path.of("src/main/resources/recommendPerson.json"));
        List<RecommendPersonVO> personList = JSONObject.parseObject(json, new TypeReference<>() {
        });

        personList = sort(personList, RecommendPersonVO::getYjCount);
        printRank("半年业绩排名：", personList, RecommendPersonVO::getYjCount);

        personList = sort(personList, RecommendPersonVO::getHyCount);
        printRank("半年行业排名：", personList, RecommendPersonVO::getHyCount);

        personList = sort(personList, RecommendPersonVO::getSkuDl);
        printRank("产品单量排名：", personList, RecommendPersonVO::getSkuDl);

        personList = sort(personList, RecommendPersonVO::getSkuYj);
        printRank("产品业绩排名：", personList, RecommendPersonVO::getSkuYj);

    }

    private static void printRank(String title, List<RecommendPersonVO> personList, Function<RecommendPersonVO, BigDecimal> countMap) {
        System.out.println("=========" + title + "==============");
        for (int i = 0; i < personList.size(); i++) {
            String content = personList.get(i).getUserName() + "-->排名:" + (i + 1) + ", 数值：" + countMap.apply(personList.get(i));
            System.out.println(content);
        }
        System.out.println();
    }


    private static List<RecommendPersonVO> sort(List<RecommendPersonVO> recommendPersons, Function<RecommendPersonVO, BigDecimal> func) {
        return recommendPersons.stream()
                .sorted((a, b) -> {
                    BigDecimal av = func.apply(a);
                    BigDecimal bv = func.apply(b);
                    av = av == null ? BigDecimal.ZERO : av;
                    bv = bv == null ? BigDecimal.ZERO : bv;
                    if (av.equals(bv)) {
                        return a.getUserId().compareTo(b.getUserId());
                    }

                    return bv.compareTo(av);
                })
                .collect(Collectors.toList());
    }


    @Data
    public static class RecommendPersonVO {

        /**
         * id
         */
        private Long userId;

        /**
         * 姓名
         */
        private String userName;

        /**
         * 职位
         */
        private String ipsPost;


        /**
         * 商机数上线
         */
        private Integer maxNum;

        /**
         * 半年总业绩
         */
        private BigDecimal yjCount;

        /**
         * 半年行业
         */
        private BigDecimal hyCount;

        /**
         * 3个月约见量
         */
        private BigDecimal apCount;

        private List<Long> userIds;

        /**
         * 产品业绩
         */
        private BigDecimal skuYj;

        /**
         * 产品单量
         */
        private BigDecimal skuDl;
    }
}
