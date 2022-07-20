package birdsnail.example.pattern.chain;

public abstract class AbstractWorker<T> implements Worker<T> {

   private AbstractWorker<?> next = null;

    @Override
    public void fireNext(Object element) {
        if (next != null) {
            next.transformNext(element);
        }
    }

    @SuppressWarnings("unchecked")
    void transformNext(Object element) {
        T o = (T) element;
        doSomething(o);
    }

    public void setNext(AbstractWorker<?> next) {
        this.next = next;
    }


    public AbstractWorker<?> getNext() {
        return this.next;
    }
}
