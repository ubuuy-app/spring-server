package com.aviobrief.springserver.db;

import org.springframework.stereotype.Component;

@Component
public record DatabaseInit(UserSeed userSeed) {

    public void initDatabase() {
        userSeed.seedUsers();
    }

}
