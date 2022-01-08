package com.aviobrief.springserver.services;
import com.aviobrief.springserver.models.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity saveOne(UserEntity userEntity); //todo -> use UserServiceModel and ModelMapper

    List<UserEntity> getAll();
}
