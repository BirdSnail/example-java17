package birdsnail.example.io;

import com.alibaba.fastjson.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import birdsnail.example.entity.Person;

class CaptureOutputTest {


    @Test
    void systemOutTest() {
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        // Print some output: goes to your special stream
        System.out.print("Foofoofoo!");
        // Put things back
        System.out.flush();
        System.setOut(old);

        Assertions.assertEquals("Foofoofoo!", baos.toString());


    }

    @Test
    void objectMapperTest() {
        Person person = new Person();
        person.setFullName("「简易上手，非常好用！」\\r「很喜欢！手绘图片很有风格。」\r「闯关模式实在是太用心的设计！");
        person.setAge(22);
        System.out.println(JSONObject.toJSONString(person));
        System.out.println(person.getFullName().replaceAll("\r", ";"));
    }


}