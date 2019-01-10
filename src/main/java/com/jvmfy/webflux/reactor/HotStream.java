package com.jvmfy.webflux.reactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;

@Slf4j
public class HotStream {

    public static void main(String[] args) throws IOException {

        EmitterProcessor<Integer> processor = EmitterProcessor.create();
        FluxSink<Integer> sink = processor.sink();
        Flux<Integer> hotFlux = processor.delayElements(Duration.ofSeconds(1)).replay().autoConnect();

        sink.next(0);
        sink.next(1);
        sink.next(2);
        sink.next(3);

        hotFlux.subscribe(value -> log.info("Subscriber 1: {}", value));

        sink.next(4);
        sink.next(5);
        sink.next(6);

        hotFlux.delaySubscription(Duration.ofSeconds(3)).subscribe(value -> log.info("Subscriber 2: {}", value));

        sink.next(7);

        new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
}
