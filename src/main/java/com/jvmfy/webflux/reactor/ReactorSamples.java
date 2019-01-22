package com.jvmfy.webflux.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ReactorSamples {

    private static String[] CARS = new String[]{
            "AUDI", "BMW", "HONDA", "MITSUBISHI"
    };

    private static String getCarByIndex(int index) {
//        log.info("Index: {}, on thread: {}", index, Thread.currentThread().getName());
        return CARS[index];
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        /*Basic flux*/
//        Flux<String> carsFlux = Flux.range(0, 4)
//                .map(ReactorSamples::getCarByIndex);
//
//        carsFlux.subscribe(c -> log.info("subscriber 1: {}", c));
//        carsFlux.subscribe(c -> log.info("subscriber 2: {}", c));


        /*Hot vs cold*/

        /*One thread hot publisher*/
//        AtomicInteger counter = new AtomicInteger();
//
//        Flux<Integer> hotFlux = Flux.generate(sink -> sink.next(counter.incrementAndGet()));
//
//        hotFlux.take(4).subscribe(v -> log.info("subscriber 1: {}", v));
//        Thread.sleep(1000);
//        hotFlux.take(4).subscribe(v -> log.info("subscriber 2: {}", v));

        /*Elastic threads hot publisher*/

//        AtomicInteger counter = new AtomicInteger();
//
//        Flux<Integer> hotFlux = Flux.<Integer>generate(sink -> sink.next(counter.incrementAndGet()))
//                .publishOn(Schedulers.elastic());
//
//        hotFlux.take(4).subscribe(v -> log.info("subscriber 1: {}. Thread: {}", v, Thread.currentThread().getName()));
//        Thread.sleep(1000);
//        hotFlux.take(4).subscribe(v -> log.info("subscriber 2: {}. Thread: {}", v, Thread.currentThread().getName()));


        /*Convert cold to hot flux*/

//        Flux<String> carsFlux = Flux.range(0, 4)
//                .map(ReactorSamples::getCarByIndex);
//
//        ConnectableFlux<String> connectableFlux = carsFlux.delayElements(Duration.ofMillis(500)).publish();
//
//        connectableFlux.subscribe(c -> log.info("subscriber 1: {}", c));
//
//        connectableFlux.connect();
//
//        Thread.sleep(1500);
//
//        connectableFlux.subscribe(c -> log.info("subscriber 2: {}", c));

        /*ConnectableFlux replay*/

//        Flux<String> carsFlux = Flux.range(0, 4)
//                .map(ReactorSamples::getCarByIndex);
//
//        ConnectableFlux<String> connectableFlux = carsFlux.delayElements(Duration.ofMillis(500))
//                .replay(2); //pay attention, we use replay instead of publish method
//
//        connectableFlux.subscribe(c -> log.info("subscriber 1: {}", c));
//
//        connectableFlux.connect();
//
//        Thread.sleep(1500);
//
//        connectableFlux.subscribe(c -> log.info("subscriber 2: {}", c));

        /*Auto-connectable flux*/

//        Flux<String> carsFlux = Flux.range(0, 4)
//                .map(ReactorSamples::getCarByIndex);
//
//        ConnectableFlux<String> connectableFlux = carsFlux.delayElements(Duration.ofMillis(500))
//                .publish();
//
//        Flux<String> autoConnectable = connectableFlux.autoConnect();
//
//        autoConnectable.subscribe(c -> log.info("subscriber 1: {}", c));
//
//        Thread.sleep(1500);
//
//        autoConnectable.subscribe(c -> log.info("subscriber 2: {}", c));

        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}

