package birdsnail.example.异步;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Void> result = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hahah");
            throw new RuntimeException("fsadfs");
        }, Executors.newFixedThreadPool(2));

        result.exceptionally(throwable -> {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(throwable.getMessage());
                    return null;
                }
        );
        // System.out.println("ss");
        // Thread.sleep(10000000L);
        System.out.println("----end----------");
    }
}
