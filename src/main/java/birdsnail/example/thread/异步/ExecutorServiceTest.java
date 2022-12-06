package birdsnail.example.thread.异步;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("");
            return 1;
        });

        future.get();

    }
}
