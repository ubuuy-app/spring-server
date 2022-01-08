package com.aviobrief.springserver.db;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public record UserSeed(UserService userService) {

    private static final Logger logger = Logger.getLogger("UserSeed");

    private static final List<String> USER_EMAILS = List.of(
            "petar.petkov@mailinator.com",
            "eli.deyanova@mailinator.com"
    );

    private static final List<String> FIRST_NAMES = List.of(
            "petar",
            "eli"
    );

    private static final List<String> LAST_NAMES = List.of(
            "petkov",
            "deyanova"
    );

    private static final List<String> PASSWORDS = List.of(
            "111111",
            "222222"
    );


    public void seedUsers() {
        USER_EMAILS
                .stream()
                .map((email) -> {
                    int currentIndex = USER_EMAILS.indexOf(email);
                    return new UserEntity(
                            email,
                            FIRST_NAMES.get(currentIndex),
                            LAST_NAMES.get(currentIndex),
                            PASSWORDS.get(currentIndex)
                    );
                })
                .forEach(userEntity -> {
                    try {
                        UserEntity savedEntity = userService.saveOne(userEntity);
                        logger.log(Level.INFO, String.format("%d", savedEntity != null ? savedEntity.getId() : -1));
                    } catch (Exception e) {
                        logger.log(Level.INFO, e::getMessage);
                    }
                });
    }
}
