package com.aviobrief.springserver.db;

import com.aviobrief.springserver.models.entities.RoleEntity;
import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.models.enums.UserRole;
import com.aviobrief.springserver.repositories.RoleRepository;
import com.aviobrief.springserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public record UserSeed(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

    private static final Logger logger = Logger.getLogger("UserSeed");

    private static final RoleEntity adminRole = new RoleEntity().setRole(UserRole.ADMIN);
    private static final RoleEntity userRole = new RoleEntity().setRole(UserRole.USER);

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
        roleRepository().saveAll(List.of(adminRole, userRole));
        USER_EMAILS
                .stream()
                .map((email) -> {
                    int currentIndex = USER_EMAILS.indexOf(email);
                    UserEntity userEntity = new UserEntity(
                            email,
                            FIRST_NAMES.get(currentIndex),
                            LAST_NAMES.get(currentIndex),
                            passwordEncoder.encode(PASSWORDS.get(currentIndex))
                    );
                    userEntity.setRoles(currentIndex == 0 ? List.of(adminRole, userRole) : List.of(userRole));
                    return userEntity;
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
