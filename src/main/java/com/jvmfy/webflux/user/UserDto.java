package com.jvmfy.webflux.user;

import com.datastax.driver.core.utils.UUIDs;
import lombok.Getter;

@Getter
public class UserDto {

    private String name;
    private String password;

    public User toUser() {
        return User.builder()
                .id(UUIDs.timeBased())
                .name(name)
                .password(password)
                .build();
    }
}