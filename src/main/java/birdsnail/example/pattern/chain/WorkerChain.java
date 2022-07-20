package birdsnail.example.pattern.chain;

public abstract class WorkerChain extends AbstractWorker<Object>{

    public abstract void addFirst(AbstractWorker<?> worker);

    public abstract void addLast(AbstractWorker<?> worker);

}
