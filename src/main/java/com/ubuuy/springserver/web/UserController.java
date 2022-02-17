package com.ubuuy.springserver.web;

import com.ubuuy.springserver.config.constants.LoggerMessages;
import com.ubuuy.springserver.models.responses.view_models.UserViewModel;
import com.ubuuy.springserver.services.UserService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class UserController {

    private static final Logger logger = Logger.getLogger("UserController");
    private final UserService userService;
    private final Mapper mapper;

    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/users")
    public DeferredResult<ResponseEntity<List<UserViewModel>>> getAll() {
        DeferredResult<ResponseEntity<List<UserViewModel>>> dr = new DeferredResult<>();

        try {
            userService
                    .getAll()
                    .thenApply(userServiceModels -> {

                        logger.log(Level.INFO, LoggerMessages.USERS_GET_ALL_OK);
                        return dr.setResult(
                                ResponseEntity
                                        .ok()
                                        .body(mapper.toModel(userServiceModels, UserViewModel.class))
                        );
                    })
                    .exceptionally(e -> {
                        System.out.println(e);
                        logger.log(Level.INFO, LoggerMessages.USERS_GET_ALL_FAIL);
                        return dr.setResult(ResponseEntity
                                .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                                .build());
                    });
            return dr;
        } catch (Exception e) {
            logger.log(Level.INFO, LoggerMessages.USERS_GET_ALL_FAIL);
            dr.setResult(ResponseEntity
                    .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build());
            return dr;
        }
    }
}
