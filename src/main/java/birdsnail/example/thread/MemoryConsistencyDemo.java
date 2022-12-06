package birdsnail.example.thread;

/**
 * 内存一致性demo: 即使线程2修改了共享变量flag=true，线程1访问到的flag依然为false.
 * <p>如果flag使用volatile关键字就会保证变量修改的可见性，线程2修改完成后会让线程1可见</p>
 */
public class MemoryConsistencyDemo {
    /**
     * 保证可见性
     */
    private static volatile boolean flag = false;
    // private static  boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("waiting");
            while (!flag) {
            }
            // 走不到这里
            System.out.println("in");
        }).start();

        Thread.sleep(2000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("change flag");
                flag = true;
                System.out.println("change success");
            }
        }).start();
    }

}
