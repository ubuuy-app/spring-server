package com.aviobrief.springserver.services;
import com.aviobrief.springserver.models.entities.UserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserEntity saveOne(UserEntity userEntity); //todo -> use UserServiceModel and ModelMapper

    CompletableFuture<List<UserEntity>> getAll() throws Exception;
}
