package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.repositories.UserRepository;
import com.aviobrief.springserver.services.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity saveOne(UserEntity userEntity) {
        try {
            return this.userRepository.save(userEntity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<List<UserEntity>> getAll() throws Exception {
        try {
            return CompletableFuture
                    .supplyAsync(userRepository::findAll)
                    .orTimeout(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //todo - change exception
        }
    }
}
