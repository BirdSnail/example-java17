package birdsnail.example.pattern.chain;

public interface Worker<T> {

    void doSomething(T element);


    /**
     * 执行责任链中下一个节点的逻辑
     */
    void fireNext(Object element);

}
