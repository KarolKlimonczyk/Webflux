package com.jvmfy.webflux.user;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@AllArgsConstructor
@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {
        return RouterFunctions.route(POST("/api/user/new").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/api/user/{userId}"), userHandler::getUser)
                .andRoute(GET("/api/users"), userHandler::getAllUsers)
                .andRoute(GET("/api/users/interval"), userHandler::getAllUsersInterval);
    }
}
