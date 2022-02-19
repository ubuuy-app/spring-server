package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.service_models.UserServiceModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserServiceModel saveNewUser(UserServiceModel userServiceModel);

    CompletableFuture<List<UserServiceModel>> getAll() throws SQLException;

    UserServiceModel getByEmail(String email) throws UsernameNotFoundException;
}
