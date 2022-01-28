package com.ubuuy.springserver.db;

import com.ubuuy.springserver.db.seed.UserSeed;
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
