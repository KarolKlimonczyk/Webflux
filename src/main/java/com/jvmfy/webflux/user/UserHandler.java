package com.jvmfy.webflux.user;

import com.jvmfy.webflux.ascii.AsciiArtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Slf4j
@AllArgsConstructor
@Component
public class UserHandler {

    private final UserRepository userRepository;
    private final AsciiArtService asciiArtService;

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
        return ok().contentType(TEXT_EVENT_STREAM).body(this.userRepository.findAll(), User.class)
                .switchIfEmpty(noContent().build());
    }

    Mono<ServerResponse> getAllUsersInterval(ServerRequest request) {
        Flux<User> users = this.userRepository.findAll();
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return ok().contentType(TEXT_EVENT_STREAM).body(
                Flux.zip(users, interval)
                        .map(Tuple2::getT1)
                        .doOnComplete(() -> log.info("Users stream completed")), User.class)
                .switchIfEmpty(noContent().build());
    }

    Mono<ServerResponse> getAllUsersNameAsAsciiArt(ServerRequest request) {
        Flux<User> users = this.userRepository.findAll();
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        return ok().contentType(TEXT_EVENT_STREAM).body(
                Flux.zip(
                        users.map(u -> this.asciiArtService.generateAsciiArt(u.getName())), interval)
                        .map(Tuple2::getT1), String.class)
                .switchIfEmpty(noContent().build());
    }
}
