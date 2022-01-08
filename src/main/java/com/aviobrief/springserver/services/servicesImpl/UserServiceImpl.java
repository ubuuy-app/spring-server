package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.entity.UserEntity;
import com.aviobrief.springserver.repositories.UserRepository;
import com.aviobrief.springserver.services.UserService;
import org.springframework.stereotype.Service;

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
        } catch (Exception e){
            return null;
        }
    }
}
