package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.entities.UserEntity;
import com.aviobrief.springserver.models.views.UserViewModel;
import com.aviobrief.springserver.repositories.UserRepository;
import com.aviobrief.springserver.services.UserService;
import com.aviobrief.springserver.utils.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.aviobrief.springserver.config.ServerConfig.COMPLETABLE_AWAIT_TIME_SEC;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepo, ModelMapper modelMapper, Mapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    public UserEntity saveOne(UserEntity userEntity) {
        try {
            return this.userRepo.save(userEntity);
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
                    .orTimeout(COMPLETABLE_AWAIT_TIME_SEC, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //todo - change exception
        }
    }
}
