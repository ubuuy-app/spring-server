package com.aviobrief.springserver.db;

import com.aviobrief.springserver.db.seed.UserSeed;
import org.springframework.stereotype.Component;

@Component
public record DatabaseInit(UserSeed userSeed) {

    public void initDatabase() {
        userSeed.seedUsers();
    }

}
