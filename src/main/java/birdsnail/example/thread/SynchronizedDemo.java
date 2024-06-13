package birdsnail.example.thread;

/**
 * 执行wait,notify方法的线程必须是对象monitor的拥有者
 */
public class SynchronizedDemo {

    public final Object lock = new Object();

    public synchronized void waitTest() throws InterruptedException {
        wait();
    }

    public synchronized void notifyTest() {
        notifyAll();
    }

    public void waitTest2() throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo demo = new SynchronizedDemo();
        new Thread(demo::notifyTest).start();
        demo.waitTest();

        demo.waitTest2();
    }

}
