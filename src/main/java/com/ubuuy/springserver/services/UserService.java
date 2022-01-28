package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.responses.UserViewModel;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserServiceModel saveOne(UserServiceModel userServiceModel);

    CompletableFuture<List<UserViewModel>> getAll() throws Exception;

    UserViewModel getByEmail(String email) throws UsernameNotFoundException;

}
