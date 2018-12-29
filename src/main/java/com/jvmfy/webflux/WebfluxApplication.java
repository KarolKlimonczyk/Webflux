package com.jvmfy.webflux;

import com.jvmfy.webflux.client.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@EnableCassandraRepositories
@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);

        Client client = new Client(WebClient.create("http://localhost:8080"));
        client.fetchUsersNamesAsAsciiArt();
    }
}