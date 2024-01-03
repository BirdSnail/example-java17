package birdsnail.example.thread;

import java.util.concurrent.ThreadFactory;

public class VirtualThreadTest {


    public static void main(String[] args) {
        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();
//        virtualThreadFactory.newThread();
    }

}
