package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.RegisterOwnerRequest;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    UserServiceModel saveOne(UserServiceModel userServiceModel);

    CompletableFuture<List<UserServiceModel>> getAll() throws Exception;

    UserServiceModel getByEmail(String email) throws UsernameNotFoundException;

    UserServiceModel registerOrganizationOwner(RegisterOwnerRequest registerOwnerRequest);

}
