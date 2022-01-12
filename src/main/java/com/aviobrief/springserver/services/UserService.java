package com.aviobrief.springserver.services;

import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.models.responses.UserViewModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserEntity saveOne(UserEntity userEntity); //todo -> use UserServiceModel and ModelMapper

    CompletableFuture<List<UserViewModel>> getAll() throws Exception;

    UserViewModel getByEmail(String email) throws UsernameNotFoundException;

}
