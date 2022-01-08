package com.aviobrief.springserver.db;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public record UserSeed(UserService userService) {

    private static final Logger logger = Logger.getLogger("UserSeed");

    private static List<String> USER_EMAILS = List.of(
            "petar.petkov@mailinator.com",
            "eli.deyanova@mailinator.com"
    );

    private static List<String> FIRST_NAMES = List.of(
            "petar",
            "eli"
    );

    private static List<String> LAST_NAMES = List.of(
            "petkov",
            "deyanova"
    );


    public void seedUsers() {
        USER_EMAILS
                .stream()
                .map((email) -> new UserEntity("todo", "todo", "todo", email))
                .forEach(userEntity -> {
                    try{
                        UserEntity savedEntity = userService.saveOne(userEntity);
                        logger.log(Level.INFO, String.format("%d", savedEntity.getId()));
                    } catch (Exception e){
                        logger.log(Level.INFO, e::getMessage);
                    }
                });
    }
}
