package birdsnail.example.pattern.chain.impl;

import java.util.List;

import birdsnail.example.pattern.chain.AbstractWorker;

public class TwoWorker extends AbstractWorker<String> {

    String str = """
            模板字符串，哈哈哈
            select * from test  where id =1
                      
            """;

    @Override
    public void doSomething(String element) {

        var list = List.of("1", "2", "3");

        System.out.println("执行第二worker");
        fireNext(element);
        System.out.println("第二个worker执行完成");
    }
}
