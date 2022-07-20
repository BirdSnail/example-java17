package birdsnail.example.pattern.chain.impl;

import birdsnail.example.pattern.chain.AbstractWorker;

public class OneWorker extends AbstractWorker<String> {

    @Override
    public void doSomething(String element) {
        System.out.println("执行第一个worker");
        fireNext(element);
        System.out.println("第一个worker执行完成");
    }

}
