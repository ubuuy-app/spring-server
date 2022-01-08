package com.aviobrief.springserver.services;
import com.aviobrief.springserver.models.entity.UserEntity;

public interface UserService {
    UserEntity saveOne(UserEntity userEntity); //todo -> use UserServiceModel and ModelMapper

}
