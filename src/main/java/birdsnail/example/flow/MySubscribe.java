package birdsnail.example.flow;

import java.util.concurrent.Flow;

public class MySubscribe<T> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }
    @Override
    public void onNext(T item) {
        System.out.println(item);
        subscription.request(1);
    }
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}
