package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.UserEntity;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import com.ubuuy.springserver.repositories.UserRepository;
import com.ubuuy.springserver.services.UserService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            Mapper mapper
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserServiceModel save(UserServiceModel userServiceModel) {
        try {
            UserEntity savedUser = this.userRepository.saveAndFlush(mapper.toModel(userServiceModel, UserEntity.class));
            return mapper.toModel(savedUser, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<List<UserServiceModel>> getAll() throws Exception {
        try {
            return CompletableFuture
                    .supplyAsync(() -> mapper.toModel(userRepository.findAll(), UserServiceModel.class))
                    .orTimeout(ApplicationConstants.COMPLETABLE_AWAIT_TIME_SEC, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //todo - change exception and message
        }
    }

    @Override
    public UserServiceModel getByEmail(String email) throws UsernameNotFoundException {

        UserEntity userEntity =
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_FIND_BY_EMAIL_FAIL));

        return mapper.toModel(userEntity, UserServiceModel.class);

    }

}
