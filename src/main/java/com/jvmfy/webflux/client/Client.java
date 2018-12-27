package com.jvmfy.webflux.client;

import com.jvmfy.webflux.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@Slf4j
public class Client {

    private final WebClient webClient;

    public Client(WebClient webClient) {
        this.webClient = webClient;
    }

    public void fetchUsers() {
        webClient.get()
                .uri("/api/users")
                .accept(APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(User.class))
                .subscribe(user -> log.info("User: {}", user));
    }
}
