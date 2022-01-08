package com.aviobrief.springserver.webControllers;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.aviobrief.springserver.utils.log.LoggerMessages.*;


@RestController
public class UserController {

    private final UserService userService;
    private static final Logger logger = Logger.getLogger("UserController");

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAll() {
        try {
            ResponseEntity<List<UserEntity>> re = ResponseEntity.ok().body(userService.getAll());
            logger.log(Level.INFO, USERS_GET_ALL_OK);
            return re;
        } catch (Exception e) {
            logger.log(Level.INFO, USERS_GET_ALL_FAIL);
            return ResponseEntity.notFound().build();
        }
    }
}
