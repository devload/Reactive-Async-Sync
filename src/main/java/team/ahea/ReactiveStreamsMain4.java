package team.ahea;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;

@Slf4j
public class ReactiveStreamsMain4 {

    public static void main(String[] args) {

        Publisher pub = subscriber -> subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long l) {

                log.debug("request - {}" , l);

                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onComplete();
            }

            @Override
            public void cancel() {
            }
        });


        Subscriber sub = new Subscriber() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.debug("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Object o) {
                log.debug("onNext - " + o);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };


        Subscriber subOnSub = new Subscriber() {

            @Override
            public void onSubscribe(Subscription subscription) {
                Executors.newSingleThreadExecutor().execute(()->sub.onSubscribe(subscription));
            }

            @Override
            public void onNext(Object o) {
                Executors.newSingleThreadExecutor().execute(()->sub.onNext(o));
            }

            @Override
            public void onError(Throwable throwable) {
                Executors.newSingleThreadExecutor().execute(()->sub.onError(throwable));
            }

            @Override
            public void onComplete() {
                Executors.newSingleThreadExecutor().execute(()->sub.onComplete());
            }
        };


        Publisher pubOnPub = subscriber -> Executors.newSingleThreadExecutor().execute(() -> pub.subscribe(subOnSub));
        pubOnPub.subscribe(subOnSub);

        log.debug("exit");

    }

}
