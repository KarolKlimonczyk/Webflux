package com.jvmfy.webflux.reactor;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;

@Slf4j
public class Backpressure {

    public static void main(String[] args) throws IOException {

        //without backpressure
        Flux.range(0, 20)
                .log()
                .subscribe();


        //with backpressure
        Flux.range(0, 20)
                .log()
                .delaySubscription(Duration.ofSeconds(3))
                .delaySequence(Duration.ofSeconds(3))
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;
                    private int dataCounter;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer value) {
                        this.dataCounter++;

                        if (this.dataCounter % 3 == 0) {
                            this.subscription.request(3);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        log.error("Cannot obtain value cause: {}", t);
                    }

                    @Override
                    public void onComplete() {
                        log.info("Completed");
                    }
                });

        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
