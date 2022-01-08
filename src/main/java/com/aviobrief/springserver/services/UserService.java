package com.aviobrief.springserver.services;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void saveOne(UserServiceModel userServiceModel);
}
