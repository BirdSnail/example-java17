package birdsnail.example.pattern.chain;

import org.junit.jupiter.api.Test;

class DefaultWorkerChainTest {


    @Test
    void doSomething() {
        WorkerChain workerChain = WorkerChainBuild.build();
        workerChain.doSomething("ok");
    }
}