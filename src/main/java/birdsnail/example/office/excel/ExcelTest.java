package birdsnail.example.office.excel;


import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
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
        List<String> values = new LinkedList<>();
        ExcelNoModelDataHandler.handleAll(file, list -> {
            for (Map<Integer, String> map : list) {
                String code = map.get(0);
                String name = map.get(1);
                String desc = map.get(2);
                String source = code.startsWith("ZHZC") ? "80026" : "80027";
                if (code.startsWith("ZDSM")) {
                    source = "";
                }
                String value = String.format("('user_behavior_clue_config', '%s', '%s', '%s', 0,'%s')",
                        code, name, source, desc);
                values.add(value);
            }
        });

        String insert =
                """
                  INSERT INTO ips_crm_db.crm_lookup_item (classify_code, item_code, item_name,  item_attr2,  status, description)
                  values %s
                """;
        String joinValues = String.join(",\n", values);
        System.out.println(String.format(insert, joinValues) + ";");
    }


}
