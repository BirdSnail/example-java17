package birdsnail.example.entity;

import com.alibaba.fastjson.JSON;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
public class Person {

    private int age;
    private String fullName;

    @Getter
    @Accessors(fluent = true)
    private boolean hasCallLog;


    public static void main(String[] args) {
        Person person = new Person();
        person.hasCallLog(true);
        System.out.println(JSON.toJSONString(person));
    }

}