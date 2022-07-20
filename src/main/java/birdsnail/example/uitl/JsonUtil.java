package birdsnail.example.uitl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

public class JsonUtil {

    /**
     * 扁平化json字符串
     */
    public static String recursionParseJsonObj(JSONObject jsonObj, final Map<String, String> fieldDictionary,
                                               final String elementDelimiter, final String keyValueDelimiter) {
        if (jsonObj == null || jsonObj.isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Object> jsonEntry : jsonObj.entrySet()) {
            Object jsonEntryValue = jsonEntry.getValue();
            if (jsonEntryValue == null) {
                continue;
            }
            if (isEmpty(jsonEntryValue)) {
                continue;
            }
            result.append(fieldDictionary.getOrDefault(jsonEntry.getKey(), jsonEntry.getKey())).append(keyValueDelimiter);
            if (jsonEntryValue instanceof String || jsonEntryValue instanceof Number) {
                result.append(jsonEntryValue);
            } else if (jsonEntryValue instanceof JSONObject) {
                String valueStr = recursionParseJsonObj((JSONObject) jsonEntryValue, fieldDictionary, elementDelimiter,
                        keyValueDelimiter);
                result.append("{").append(valueStr).append("}");
            } else if (jsonEntryValue instanceof JSONArray jsonArray) {
                result.append("[");
                for (int i = 0; i < jsonArray.size(); i++) {
                    String jsonArrayStr = recursionParseJsonObj(jsonArray.getJSONObject(i), fieldDictionary,
                            elementDelimiter, keyValueDelimiter);
                    result.append(jsonArrayStr);
                }
                result.append("]");
            }

            result.append(elementDelimiter);
        }

        return result.toString().replaceAll(elementDelimiter + "$", "");
    }

    private static boolean isEmpty(Object valueObject) {
        if (valueObject instanceof String) {
            return StringUtils.isBlank((String) valueObject);
        }
        if (valueObject instanceof JSONObject jsonObj) {
            return jsonObj.isEmpty();
        }
        if (valueObject instanceof JSONArray jsonArr) {
            return jsonArr.isEmpty();
        }
        return valueObject == null;
    }

    public static void main(String[] args) {

        String json = """
                {
                    "article": {
                        "data": [],
                        "total": 16,
                        "index": 13
                    },
                    "expert": {
                        "data": [],
                        "total": 2858,
                        "index": 10
                    },
                    "patent": {
                        "data": [],
                        "total": 14,
                        "index": 2
                    },
                    "achievement": {
                        "data": [],
                        "total": 4044,
                        "index": 9
                    },
                    "enterprise": {
                        "data": [],
                        "total": 3,
                        "index": 1
                    },
                    "public_document": {
                        "data": [],
                        "total": 168484,
                        "index": 4,
                        "ret_code": 0
                    },
                    "policy_document": {
                        "data": [],
                        "total": 48212,
                        "index": 5,
                        "ret_code": 0
                    },
                    "subsidy_document": {
                        "data": [],
                        "total": 0,
                        "index": 3,
                        "ret_code": 0
                    },
                    "application_document": {
                        "data": [],
                        "total": 41767,
                        "index": 6,
                        "ret_code": 0
                    },
                    "company_document": {
                        "data": [],
                        "total": 3973917,
                        "index": 7,
                        "ret_code": 0
                    },
                    "project_document": {
                        "data": [],
                        "total": 1126,
                        "index": 8,
                        "ret_code": 0
                    },
                    "trademark": {
                        "data": [],
                        "total": 2,
                        "index": 11
                    },
                    "copyright": {
                        "data": [],
                        "total": 12,
                        "index": 12
                    },
                    "intent": "company_name",
                    "source": "rule",
                    "ret_code": 0
                }
                """;

        System.out.println(recursionParseJsonObj(JSONObject.parseObject(json), Collections.emptyMap(), ",", ":"));

    }
}
