package birdsnail.example.pattern.chain;

import java.util.ServiceLoader;

public class WorkerChainBuild {


    public static WorkerChain build() {

        WorkerChain defaultWorkerChain = new DefaultWorkerChain();

        var workers = ServiceLoader.load(Worker.class);
        for (var worker : workers) {
            if (!(worker instanceof AbstractWorker)) {
                System.out.println("不是AbstractWorker子类");
                continue;
            }
            defaultWorkerChain.addLast((AbstractWorker<?>) worker);
        }

        return defaultWorkerChain;

    }


}
