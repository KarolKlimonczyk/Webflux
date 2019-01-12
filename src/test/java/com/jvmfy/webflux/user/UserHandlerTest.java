package com.jvmfy.webflux.user;

import com.jvmfy.webflux.ascii.AsciiArtService;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

public class UserHandlerTest {

    @Test
    public void shouldReturnAllUsers() {
        //given
        UserRouter userRouter = new UserRouter();
        UserRepository userRepository = mock(UserRepository.class);
        Flux<User> users = this.createUsers();
        when(userRepository.findAll()).thenReturn(users);
        UserHandler userHandler = new UserHandler(userRepository, new AsciiArtService());
        WebTestClient webTestClient = WebTestClient.bindToRouterFunction(userRouter.route(userHandler)).build();
        List<User> userList = users.collectList().block();

        //when
        webTestClient.get()
                .uri("/api/users/interval")
                .accept(TEXT_EVENT_STREAM)
                .exchange()

                //then
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .hasSize(3).contains(userList.get(0), userList.get(1), userList.get(2));
    }

    private Flux<User> createUsers() {
        return Flux.just(new User(UUID.randomUUID(), "user1", "pass"),
                new User(UUID.randomUUID(), "user2", "pass"),
                new User(UUID.randomUUID(), "user3", "pass")
        );
    }
}