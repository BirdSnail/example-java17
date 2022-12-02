package birdsnail.example.异步;

import java.util.List;
import java.util.concurrent.*;

import lombok.Data;

public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // CompletableFuture<Void> result = CompletableFuture.runAsync(() -> {
        //     try {
        //         Thread.sleep(2000L);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        //     System.out.println("hahah");
        //     throw new RuntimeException("fsadfs");
        // }, Executors.newFixedThreadPool(2));
        //
        // result.exceptionally(throwable -> {
        //             try {
        //                 Thread.sleep(5000L);
        //             } catch (InterruptedException e) {
        //                 e.printStackTrace();
        //             }
        //
        //             System.out.println(throwable.getMessage());
        //             return null;
        //         }
        // );
        // System.out.println("ss");
        // Thread.sleep(10000000L);
        System.out.println("----end----------");
        // forkJoinTest();
        forkJoinTest2();
    }


    public static void forkJoinTest() throws ExecutionException, InterruptedException {
        List<String> resultCollector = new CopyOnWriteArrayList<>();

        ExecutorService pool = Executors.newFixedThreadPool(6);

        long start = System.currentTimeMillis();
        // 以下三个任务同步执行
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> action("111", 2000, resultCollector), pool);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> action("2222", 20, resultCollector), pool);
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> action("333", 20, resultCollector), pool);

        CompletableFuture<Void> res = CompletableFuture.allOf(future1, future2, future3);
        res.get(); // 阻塞，直到三个任务完成
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        System.out.println(resultCollector);
        pool.shutdown();
    }

    public static void forkJoinTest2() throws ExecutionException, InterruptedException {
        List<String> resultCollector = new CopyOnWriteArrayList<>();

        ExecutorService pool = Executors.newFixedThreadPool(6);

        long start = System.currentTimeMillis();
        // 以下三个任务同步执行
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(CompletableFutureTest::genRandom, pool)
                .thenAcceptAsync(num -> action("1111", 2000, resultCollector)); // 提供一个回调，结果放入list
        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(CompletableFutureTest::genRandom, pool)
                .thenAcceptAsync(num -> action("2222", 20, resultCollector));
        CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(CompletableFutureTest::genRandom, pool)
                .thenAcceptAsync(num -> action("3333", 200, resultCollector));

        CompletableFuture<Void> res = CompletableFuture.allOf(future1, future2, future3);
        res.join(); // 阻塞，直到三个任务完成
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        System.out.println(resultCollector);
        pool.shutdown();
    }

    // 产生一个随机数
    private static int genRandom() {
        return ThreadLocalRandom.current().nextInt(0, 5000);
    }

    private static void action(String order, long sleep, List<String> collector) {
        collector.add(genRandom() + "");
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行了:" + order);
    }


    public static void concurrentQuery() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(6);

        DataInfo dataInfo = new DataInfo();

        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(CompletableFutureTest::query技术信息, pool)
                .thenAcceptAsync(dataInfo::set技术信息);
        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(CompletableFutureTest::query第一页成果, pool)
                .thenAcceptAsync(dataInfo::set第一页成果);
        CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(CompletableFutureTest::query第一页专家, pool)
                .thenAcceptAsync(dataInfo::set第一页专家);

        CompletableFuture.allOf(future1, future2, future3).join();
        System.out.println(dataInfo);
    }

    private static String query第一页专家() {
        return "524212";
    }

    private static String query第一页成果() {
        return "fasf";
    }

    private static String query技术信息() {
        return "jishisjishi ";
    }

    @Data
    static class DataInfo{
        private Object 技术信息;
        private Object 第一页成果;
        private Object 第一页专家;

    }
}
