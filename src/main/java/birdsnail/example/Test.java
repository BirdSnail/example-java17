package birdsnail.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import birdsnail.example.flow.MySubscribe;

public class Test {


    public static void main(String[] args) {
        System.out.println(1 >> 1);

        List<Object> list = new ArrayList<>();

        System.out.println(list.stream().anyMatch(it -> it.equals("hah")));

        System.out.println(MySubscribe.class.getTypeName());
        System.out.println(MySubscribe.class.getName());

        System.out.println(new HashMap<>().get(null));
    }
}
