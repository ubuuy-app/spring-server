package com.aviobrief.springserver.db;

import com.aviobrief.springserver.db.seed.UserSeed;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit {

    private final UserSeed userSeed;

    public DatabaseInit(UserSeed userSeed) {
        this.userSeed = userSeed;
    }

    public void initDatabase() {
//        userSeed.seedUsers();
    }




}
