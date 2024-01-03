package birdsnail.example.base;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class JsonDemo {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        User user = new User();
        user.setName("HUFANGXIA");
        user.setTags(List.of("无敌", "lol"));
        Address address = new Address();
        user.setAddress(address);
        String jsonString = toJSONString(user);
        System.out.println("by me :\t\t\t" + jsonString);
        System.out.println("by fastjson :\t" + JSON.toJSONString(user));
        System.out.println("by jackson :\t" + objectMapper.writeValueAsString(user));
    }


    public static String toJSONString(Object obj) throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");

        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();

        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                if (i != 0) {
                    stringBuffer.append(",");
                }
                Field field = fields[i];
                String name = field.getName();
                // "name":
                stringBuffer.append("\"").append(name).append("\"").append(":");

                Object valueObj;
                String value;
                if (field.isAccessible()) {
                    valueObj = field.get(obj);
                } else {
                    valueObj = getFieldValueByGetter(obj, name, methods);
                }
                if (valueObj == null) {
                    stringBuffer.append("null");
                } else {
                    // 如果valueObj不是基本类型的话，也要转成json格式
                    if (valueObj instanceof Boolean || valueObj instanceof Short || valueObj instanceof Integer || valueObj instanceof Long
                            || valueObj instanceof Float || valueObj instanceof Double
                            || valueObj instanceof String) {
                        value = valueObj.toString();
                        stringBuffer.append("\"").append(value).append("\"");
                    } else {
                        value = toJSONString(valueObj);
                        stringBuffer.append(value);
                    }
                }
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    private static Object getFieldValueByGetter(Object obj, String fieldName, Method[] methods) throws InvocationTargetException, IllegalAccessException {
        if (fieldName == null || methods == null || fieldName.length() == 0) {
            return null;
        }
        StringBuffer getter = new StringBuffer("get").append(fieldName.substring(0, 1).toUpperCase());
        if (fieldName.length() > 1) {
            getter.append(fieldName.substring(1));
        }
        for (Method method : methods) {
            if (getter.toString().equals(method.getName()) && method.getParameterCount() == 0) {
                return method.invoke(obj);
            }
        }
        return null;
    }


    @Data
    public static class User {
        private String name;
        private Integer age;

        private Address address;

        private List<String> tags;
    }

    @Data
    public static class Address {
        private String province;
        private Integer city;
    }

}
