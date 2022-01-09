package com.aviobrief.springserver.webControllers;

import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aviobrief.springserver.utils.log.LoggerMessages.USERS_GET_ALL_FAIL;
import static com.aviobrief.springserver.utils.log.LoggerMessages.USERS_GET_ALL_OK;


@RestController
public class UserController {

    private static final Logger logger = Logger.getLogger("UserController");
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public DeferredResult<ResponseEntity<List<UserEntity>>> getAll() {
        DeferredResult<ResponseEntity<List<UserEntity>>> dr = new DeferredResult<>();

        try {
            userService
                    .getAll()
                    .thenApply(res -> {
                        logger.log(Level.INFO, USERS_GET_ALL_OK);
                        return dr.setResult(ResponseEntity.ok().body(res));
                    })
                    .exceptionally(e -> {
                        logger.log(Level.INFO, USERS_GET_ALL_FAIL);
                        return dr.setResult(ResponseEntity
                                .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                                .build());
                    });
            return dr;
        } catch (Exception e) {
            logger.log(Level.INFO, USERS_GET_ALL_FAIL);
            dr.setResult(ResponseEntity
                    .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build());
            return dr;
        }
    }
}
