package birdsnail.example.算法;

import com.alibaba.excel.EasyExcel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpertHeadPortraitRecognizeUtil {

    public static String dir = "/Users/hufangxia/supercode/qzd/orc-demo/专家头像（9190个）";

    public static void main(String args[]) throws IOException {

        long startT = System.currentTimeMillis();

        List<ExpertModel> list = Files.list(Paths.get(dir))
                .map(path -> path.toFile())
                .filter(file -> !file.getName().startsWith("自动生成_"))
                .map(file -> {
                    try {
                        return recognize(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ExpertModel model = new ExpertModel();
                    model.setName(file.getName());
                    return model;
                })
                .filter(expertModel -> expertModel != null)
                .collect(Collectors.toList());

        log.info("耗时:{}", System.currentTimeMillis() - startT);

        EasyExcel.write("result.xlsx", ExpertModel.class)
                .sheet()
                .doWrite(list);

    }

    public static ExpertModel recognize(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();
        // 总的像素点
        Integer pixels = width * height;
        // 颜色匹配成功的像素点
        int matched = 0;
        // 存储各颜色出现的次数
        Map<String, Integer> colorMap = new HashMap<>();
        // 遍历每个像素点，判断颜色是否匹配
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = image.getRGB(i, j);
                //int a = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                // 蓝色背景 rgb(57, 129, 244)
                // 白色字体 rgb(255, 255, 255)
                // 只要在这两个颜色 rgb各分量 +-15 ，认为匹配成功
                if (colorMatched(r, g, b, 15)) {
                    matched++;
                }

                String color = "rgb(" + r + "," + g + "," + b + ")";
                if (!colorMap.containsKey(color)) {
                    colorMap.put(color, 1);
                } else {
                    colorMap.put(color, colorMap.get(color) + 1);
                }
            }
        }
        //        log.info("总的颜色数量：{}", colorMap.size());
        //        log.info("总的像素点：{}", pixels);
        //        log.info("颜色匹配成功的像素点：{}", matched);
        //        log.info("匹配率：{}%", matched * 100f / pixels);

        ExpertModel model = new ExpertModel();
        model.setName(file.getName());
        model.setPercent(matched * 100f / pixels);
        model.setRecognize(model.getPercent() > 90 ? true : false);
        log.info("go--->{}", model);
        if (model.getRecognize()) {
            String name = dir + "/自动生成_" + file.getName();
            file.renameTo(new File(name));
        }
        return model;
    }

    public static boolean colorMatched(int r, int g, int b, int tolerance) {
        return ((r >= 57 - tolerance && r <= 57 + tolerance
                && g >= 129 - tolerance && g <= 129 + tolerance
                && b >= 244 - tolerance && b <= 244 + tolerance)
                ||
                (r >= 255 - tolerance && g >= 255 - tolerance && b >= 255 - tolerance));
    }


    /**
     * @author fangxia.hu
     * @version 1.0.0
     * @Description:
     * @Date: 2022/11/8 12:38 PM
     */
    @Data
    public static class ExpertModel {

        private String name;
        private Boolean recognize;
        private Float percent;

    }


}