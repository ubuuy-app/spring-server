package com.aviobrief.springserver.webControllers;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserEntity> getAll(){
        return userService.getAll();
    }
}
