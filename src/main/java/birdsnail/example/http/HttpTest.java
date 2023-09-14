package birdsnail.example.http;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTest {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static HttpResponse<String> createTemplate(String param) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .uri(URI.create("http://smstpl.monyun.cn:9275/sms/v3/std/tpl/tpl_upload"))
                // .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // print status code
        // System.out.println(response.statusCode());

        System.out.println("创建模板response：\n" + response.body());


        return response;
    }

    private static String padValue(int value) {
        return StringUtils.leftPad(value + "", 2, "0");
    }

    public static String MD5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            //把字符串转换成字节码的形式
            byte[] strTemp = s.getBytes();
            //申明mdTemp为MD5加密的形式
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            //进行字节加密并行进加密 转化成16位字节码的形式
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            //j=32
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            //对字符串进行重新编码成32位的形式
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);

        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        String body = """
                {
                  "userid": "JG8852",
                  "pwd": "%s",
                  "timestamp": "%s",
                  "type": "1",
                  "expire": "60",
                  "content": "hello yhd,您有一笔#p_1#的创新金现已使用，您可在APP中查看订单详情。有效期截止#p_2#。戳>s-dev.com/#p_3#",
                  "name": ""
                }
                """;

        // add json header
        // String timestamp = getTimestamp();
        // String pwd = getEncryptPwd(timestamp);
        // String json = String.format(body, pwd, timestamp);

        // HttpResponse<String> response = createTemplate(json);

        // JSONObject jsonResponse = JSONObject.parseObject(response.body());
        // String tplid = jsonResponse.getString("tplid");
        // "pwd": "c07f40730c3452928028ef7fbc18e836",
        //         "timestamp": "0808105536",
        // "tplid":"700013025"
        queryTemplate("700013025");
    }

    private static String getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return padValue(now.getMonthValue()) + padValue(now.getDayOfMonth()) + padValue(now.getHour()) + padValue(now.getMinute()) + padValue(now.getSecond());
    }

    private static String getEncryptPwd(String timestamp) {
        String pwd = "JG8852" + "00000000" + "176698" + timestamp;
        System.out.println("密码：" + pwd);
        return MD5(pwd);
    }

    // 执行查询
    private static void queryTemplate(String tplid) throws IOException, InterruptedException {
        String templateQueryParam = """
                {
                    "userid": "JG8852",
                    "pwd": "%s",
                    "timestamp": "%s",
                    "tplid": "%s"
                }
                """;
        String timestamp = getTimestamp();
        String encryptPwd = getEncryptPwd(timestamp);
        String json2 = String.format(templateQueryParam, encryptPwd, timestamp, tplid);
        System.out.println("模板查询参数：\n" + json2);

        HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json2))
                .uri(URI.create("http://smstpl.monyun.cn:9275/sms/v3/std/tpl/tplsts_query"))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response2 = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println(response2.body());
    }


}
