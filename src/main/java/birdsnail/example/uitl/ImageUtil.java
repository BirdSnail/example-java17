package birdsnail.example.uitl;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class ImageUtil {

    private static final int LENGTH = 166;
    private static final int INTERVAL = 4;
    /**
     * 图片格式
     */
    private static final String PICTURE_FORMATE = "png";


    /**
     * 生成组合头像,类似与微信群聊天的头像.
     * 画布宽度和高度为166，图片间距为2
     *
     * @param imageUrlList 用户头像路径列表
     * @param name         上传到阿里云时指定的文件名称
     * @param appCode      应用code，取当前项目名称
     * @return 组合后的图片阿里云地址
     */
    public static String combineByImageUrl(List<String> imageUrlList, String name, String appCode) throws IOException, InterruptedException {
        return combineByImageUrl(imageUrlList, name, appCode, LENGTH, INTERVAL);
    }

    /**
     * 生成组合头像,类似与微信群聊天的头像.图片来源与网络请求
     * 画布宽度和高度为166，图片间距为2
     *
     * @param imageUrlList 图片网络地址
     * @param name         上传到阿里云时指定的文件名称
     * @param appCode      应用code，取当前项目名称
     * @return 组合后的图片阿里云地址
     */
    public static String combineByImageUrl(List<String> imageUrlList, String name, String appCode, int length, int interval) throws IOException, InterruptedException {
        for (String url : imageUrlList) {
            if (!url.startsWith("http")) {
                throw new IllegalArgumentException("存在不符合要求的文件url->" + url);
            }
        }

        List<BufferedImage> bufferedImages = new ArrayList<>();
        int width = getWidth(imageUrlList.size(), length, interval);
        // todo 使用多线程
        for (String imageUrl : imageUrlList) {
            // 1.根据url下载图片并包装到inputStream
            try (InputStream fileInputStream = getFileInputStreamByHttp(imageUrl)) {
                if (fileInputStream != null) {
                    BufferedImage newImage = resize(fileInputStream, width, width, false);
                    bufferedImages.add(newImage);
                }
            }
        }

        // 2.生成组合图片
        BufferedImage outImage = generate(bufferedImages, length, interval);
        byte[] bytes = toByteArray(outImage);
        return uploadFileToAliyun(name, appCode, bytes);
    }

    /**
     * 生成组合头像,类似与微信群聊天的头像.图片来源于本地文件地址
     * 画布宽度和高度为166，图片间距为2
     *
     * @param filePaths 本地图片路径
     * @param name      上传到阿里云时指定的文件名称
     * @return 组合后的图片阿里云地址
     */
    public static String combineByFilePath(List<String> filePaths, String name) throws IOException {
        return combineByFilePath(filePaths, name, LENGTH, INTERVAL);
    }

    /**
     * 生成组合头像,类似与微信群聊天的头像.图片来源于本地文件地址
     *
     * @param filePaths 本地图片路径
     * @param name      文件存放路径
     * @param length    图片长度
     * @param interval  图片间隔
     * @return 组合后的图片阿里云地址
     */
    public static String combineByFilePath(List<String> filePaths, String name, int length, int interval) throws IOException {
        List<BufferedImage> bufferedImages = new ArrayList<>();
        int width = getWidth(filePaths.size(), length, interval);
        for (String path : filePaths) {
            // 1.读取本地文件并包装到inputStream
            try (InputStream fileInputStream = getFileInputStreamByPath(path)) {
                if (fileInputStream != null) {
                    BufferedImage newImage = resize(fileInputStream, width, width, false);
                    bufferedImages.add(newImage);
                }
            }
        }

        // 2.生成组合图片
        BufferedImage outImage = generate(bufferedImages, length, interval);
        byte[] bytes = toByteArray(outImage);
        return Files.write(Paths.get(name), bytes).toString();
    }

    private static InputStream getFileInputStreamByPath(String path) {
        Path filePath = Paths.get(path);
        try {
            return Files.newInputStream(filePath);
        } catch (Exception e) {
            log.warn("读取文件异常：{}", e.getMessage());
        }
        return null;
    }

    // 上传阿里云
    private static String uploadFileToAliyun(String name, String appCode, byte[] bytes) {
        if (bytes == null || bytes.length > 0) {
            // FileSDKClient fileSdkClient = getFileSdkClient();
            // JsonFile jsonFile = fileSdkClient.sendFileCreate(appCode, bytes, name);
            // log.debug("组合图片上传阿里云返回jsonFile：{}", JSON.toJSONString(jsonFile));
            // return jsonFile.getDownloadLocation();
        }
        return null;
    }

    private static InputStream getFileInputStreamByHttp(String fileUrl) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(fileUrl))
                .build();
        HttpResponse<InputStream> response =
                HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
        return response.body();
    }

    private static int getWidth(int size, int length, int interval) {
        int wh = (length - interval * 4) / 3; // 每个图片的宽高和高度：图片数>4
        if (size == 1) {
            wh = length - interval * 2; // 每个图片的宽高和高度：图片数=1
        }
        if (size > 1 && size < 5) {
            wh = (length - interval * 3) / 2; // 每个图片的宽高和高度：图片数>0并且<5
        }
        return wh;
    }

    /**
     * 生成组合头像
     *
     * @param bufferedImages 图片buffer
     * @param length         画板的宽高和高度
     * @param interval       画板中的图片间距
     */
    public static BufferedImage generate(List<BufferedImage> bufferedImages, int length, int interval) throws IOException {
        if (CollectionUtils.isEmpty(bufferedImages)) {
            throw new IllegalArgumentException("bufferedImages为空");
        }

        int wh = getWidth(bufferedImages.size(), length, interval);
        // BufferedImage.TYPE_INT_RGB可以自己定义可查看API
        BufferedImage outImage = new BufferedImage(length, length, BufferedImage.TYPE_INT_RGB);
        // 生成画布
        Graphics g = outImage.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        // 设置背景色
        g2d.setBackground(new Color(255, 255, 255));
        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2d.clearRect(0, 0, length, length);
        // 开始拼凑 根据图片的数量判断该生成那种样式的组合头像
        for (int i = 1; i <= bufferedImages.size(); i++) {
            int j = i % 3 + 1;
            if (bufferedImages.size() < 5) {
                j = i % 2 + 1;
            }
            int x = interval * j + wh * (j - 1);
            int split = (wh + interval) / 2;
            if (bufferedImages.size() == 9) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh * 2 + interval * 3, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x, interval, null);
                }
            } else if (bufferedImages.size() == 8) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh * 2 + interval * 3, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x - split, interval, null);
                }
            } else if (bufferedImages.size() == 7) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh * 2 + interval * 3, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), (length - wh) / 2, interval, null);
                }
            } else if (bufferedImages.size() == 6) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh * 2 + interval * 3 - split, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2 - split, null);
                }
            } else if (bufferedImages.size() == 5) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh * 2 + interval * 3 - split, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x - split, wh + interval * 2 - split, null);
                }
            } else if (bufferedImages.size() == 4) {
                if (i <= 2) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x, interval, null);
                }
            } else if (bufferedImages.size() == 3) {
                if (i <= 2) {
                    g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), x - split, interval, null);
                }
            } else if (bufferedImages.size() == 2) {
                g2d.drawImage(bufferedImages.get(i - 1), x, wh + interval * 2 - split, null);
            } else if (bufferedImages.size() == 1) {
                g2d.drawImage(bufferedImages.get(i - 1), interval, interval, null);
            }
            // 需要改变颜色的话在这里绘上颜色。可能会用到AlphaComposite类
        }
        return outImage;
    }

    private static byte[] toByteArray(BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return null;
        }
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufferedImage, PICTURE_FORMATE, imOut);
            return bs.toByteArray();
        } catch (IOException e) {
            log.error("BufferedImage转换为InputStream出错", e);
        }
        return new byte[0];
    }

    // private static FileSDKClient getFileSdkClient() {
    //     return SpringContextUtil.getBean(FileSDKClient.class);
    // }

    // public static BufferedImage resize(String filePath, int width, int height) {
    //     try {
    //         BufferedImage bi = ImageIO.read(new File(filePath));
    //         bi.getSubimage(0, 0, width, height);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    /**
     * 图片缩放
     *
     * @param inputStream 图片路径
     * @param height      高度
     * @param width       宽度
     * @param bb          比例不对时是否需要补白
     */
    public static BufferedImage resize(InputStream inputStream, int height, int width, boolean bb) throws IOException {

        double ratio = 0; // 缩放比例
        BufferedImage bi = ImageIO.read(inputStream);
        Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // 计算比例
        if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
            if (bi.getHeight() > bi.getWidth()) {
                ratio = (Integer.valueOf(height)).doubleValue() / bi.getHeight();
            } else {
                ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(bi, null);
        }
        if (bb) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null)) {
                g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            } else {
                g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            }
            g.dispose();
            itemp = image;
        }
        return (BufferedImage) itemp;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<String> picUrls = new ArrayList<>();
        String pic = "C:\\Users\\huadong.yang\\Desktop\\";
        picUrls.add(pic + "b08ce849c66c40199cd21a04d9244107.png");
        picUrls.add(pic + "efd383130d814a4fabeb3649a16a36c6.png");
        picUrls.add(pic + "d64dd2c31acb4c5fac645c2affe75bef.png");
        picUrls.add(pic + "e68aac431fb24b469c47fa79e4bdb325.png");
        picUrls.add(pic + "4a2f54f1c06244a8a8cead28bcb2763a.png");
        //        picUrls.add(pic + "6.jpg");
        //        picUrls.add(pic + "7.jpg");
        //        picUrls.add(pic + "8.jpg");
        //        picUrls.add(pic + "9.jpg");

        // InputStream fileInputStream = getFileInputStreamByHttp("");
        // byte[] targetArray = IOUtils.toByteArray(fileInputStream);
        // Files.write(Paths.get("xxfsaf.png"), targetArray);

        // String path = combineByFilePath(picUrls, "组合图片.png");
        // System.out.println("生成的图片地址: " + path);
        System.out.println(Arrays.toString(ImageIO.getReaderFormatNames()));
        InputStream fileInputStreamByHttp = getFileInputStreamByHttp("https://public-oss-pre.qizhidao.com/project-vue/202302/2022f47bebb044fd924f445b6fe272aa.jpeg");
        // Files.write(Path.of("xxx"), IOUtils.toByteArray(fileInputStreamByHttp));
        System.out.println(ImageIO.read(fileInputStreamByHttp));
    }
}
