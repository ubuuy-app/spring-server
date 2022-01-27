package com.aviobrief.springserver.db.seed;

import com.aviobrief.springserver.models.entities.Meta;
import com.aviobrief.springserver.models.entities.RoleEntity;
import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.models.enums.UserRole;
import com.aviobrief.springserver.repositories.RoleRepository;
import com.aviobrief.springserver.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UserSeed {

    private static final Logger logger = Logger.getLogger("UserSeed");

    private static final RoleEntity adminRole = new RoleEntity().setRole(UserRole.ADMIN);
    private static final RoleEntity userRole = new RoleEntity().setRole(UserRole.USER);

    private static final List<String> USER_EMAILS = List.of(
            "petar.petkov@mailinator.com",
            "eli.deyanova@mailinator.com"
    );
    private static final List<String> FULL_NAMES = List.of(
            "petar petkov",
            "eli deyanova"
    );

    private static final List<String> PASSWORDS = List.of(
            "111111",
            "222222"
    );
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSeed(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void seedUsers() {
        roleRepository().saveAll(List.of(adminRole, userRole));
        USER_EMAILS
                .stream()
                .map((email) -> {
                    int currentIndex = USER_EMAILS.indexOf(email);
                    UserEntity userEntity = new UserEntity(
                            email,
                            FULL_NAMES.get(currentIndex),
                            passwordEncoder.encode(PASSWORDS.get(currentIndex))
                    );
                    userEntity.setRoles(currentIndex == 0 ? List.of(adminRole, userRole) : List.of(userRole));
                    userEntity.setMeta(
                            new Meta()
                                    .setAddedAt(ZonedDateTime.now().toString())
                                    .setAddedBy("development"));

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

    public UserService userService() {
        return userService;
    }

    public RoleRepository roleRepository() {
        return roleRepository;
    }

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserSeed) obj;
        return Objects.equals(this.userService, that.userService) &&
                Objects.equals(this.roleRepository, that.roleRepository) &&
                Objects.equals(this.passwordEncoder, that.passwordEncoder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userService, roleRepository, passwordEncoder);
    }

    @Override
    public String toString() {
        return "UserSeed[" +
                "userService=" + userService + ", " +
                "roleRepository=" + roleRepository + ", " +
                "passwordEncoder=" + passwordEncoder + ']';
    }

}
