package birdsnail.example.pattern.chain;

import java.util.Objects;

public class DefaultWorkerChain extends WorkerChain {

    private final AbstractWorker<?> first = new AbstractWorker<>() {

        /**
         * 直接触发下一个节点的处理逻辑
         */
        @Override
        public void doSomething(Object element) {
            super.fireNext(element);
        }
    };
    private AbstractWorker<?> end = first;


    @Override
    public void doSomething(Object element) {
        first.transformNext(element);
    }

    @Override
    public void addFirst(AbstractWorker<?> worker) {
        Objects.requireNonNull(worker);

        worker.setNext(first.getNext());
        first.setNext(worker);
        if (first == end) {
            end = worker;
        }
    }

    @Override
    public void addLast(AbstractWorker<?> worker) {
        end.setNext(worker);
        end = worker;
    }
}
