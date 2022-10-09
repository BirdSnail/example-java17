package birdsnail.example.excel;

import com.alibaba.fastjson.JSONObject;

public class ExcelTest {

    public static void main(String[] args) {
        JSONObject content = new JSONObject();
        content.put("appName", "");
        content.put("smsType", 11);
        content.put("urlOfMsg", "hhht");
        content.put("phone", "182756351");

        System.out.println(content.toString());
        System.out.println(content.toJSONString());
        System.out.println(JSONObject.toJSONString(content.toString()));
    }


}
