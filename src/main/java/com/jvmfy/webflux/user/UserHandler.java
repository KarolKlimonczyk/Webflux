package com.jvmfy.webflux.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Slf4j
@AllArgsConstructor
@Component
public class UserHandler {

    private final UserRepository userRepository;

    Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserDto.class)
                .map(userDto -> this.userRepository.save(userDto.toUser()))
                .flatMap(u -> ok().contentType(APPLICATION_JSON).body(u, User.class))
                .switchIfEmpty(notFound().build());
    }

    Mono<ServerResponse> getUser(ServerRequest request) {
        Mono<User> user = this.userRepository.findById(UUID.fromString(request.pathVariable("userId")));

        return user
                .flatMap(u -> ok().body(user, User.class))
                .switchIfEmpty(notFound().build());
    }

    Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = this.userRepository.findAll();

        return users
                .collectList()
                .flatMap(us -> ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(users, User.class))
                .switchIfEmpty(noContent().build());
    }
}
