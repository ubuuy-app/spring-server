package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.UserEntity;
import com.ubuuy.springserver.models.responses.UserViewModel;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import com.ubuuy.springserver.repositories.UserRepository;
import com.ubuuy.springserver.services.UserService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepo, Mapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    public UserServiceModel saveOne(UserServiceModel userServiceModel) {
        try {
            UserEntity savedUser = this.userRepo.saveAndFlush(mapper.toModel(userServiceModel, UserEntity.class));
            return mapper.toModel(savedUser, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<List<UserViewModel>> getAll() throws Exception {
        try {
            return CompletableFuture
                    .supplyAsync(() -> mapper.toModel(userRepo.findAll(), UserViewModel.class))
                    .orTimeout(ApplicationConstants.COMPLETABLE_AWAIT_TIME_SEC, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //todo - change exception and message
        }
    }

    @Override
    public UserViewModel getByEmail(String email) throws UsernameNotFoundException {

        UserEntity userEntity =
                userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND_IN_DATABASE_BY_EMAIL));

        return mapper.toModel(userEntity, UserViewModel.class);

    }
}
