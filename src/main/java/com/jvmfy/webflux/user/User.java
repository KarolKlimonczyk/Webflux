package com.jvmfy.webflux.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table("USERS")
class User {

    @PrimaryKey("id")
    private UUID id;
    private String name;
    private String password;
}