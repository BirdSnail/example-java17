package birdsnail.example.excel;


import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ExcelTest {

    public static void main(String[] args) {
//        JSONObject content = new JSONObject();
//        content.put("appName", "");
//        content.put("smsType", 11);
//        content.put("urlOfMsg", "hhht");
//        content.put("phone", "182756351");
//
//        System.out.println(content.toString());
//        System.out.println(content.toJSONString());
//        System.out.println(JSONObject.toJSONString(content.toString()));

        readExcel();
    }

    private static void readExcel() {
        String file = "C:\\Users\\huadong.yang\\Desktop\\user_code.xlsx";
        ExcelNoModelDataHandler.handleAll(file, list -> {
            for (Map<Integer, String> map : list) {
                String code = map.get(0);
                String name = map.get(1);
                String source = code.startsWith("ZHZC") ? "80026" : "80027";
                if (code.startsWith("ZDSM")) {
                    source = "";
                }
                log.info("INSERT INTO ips_crm_db.crm_lookup_item (classify_code, item_code, item_name, item_attr1, item_attr2,  status) " +
                        "VALUES ( 'user_behavior_clue_config', '{}', '{}', '{}','{}', 0);", code, name, 3, source);
            }
        });
    }


}
