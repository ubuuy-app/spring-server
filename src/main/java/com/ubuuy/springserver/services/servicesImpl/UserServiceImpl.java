package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.Meta;
import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.entities.RoleEntity;
import com.ubuuy.springserver.models.entities.UserEntity;
import com.ubuuy.springserver.models.enums.UserRole;
import com.ubuuy.springserver.models.requests.RegisterOwnerRequest;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.UserServiceModel;
import com.ubuuy.springserver.repositories.UserRepository;
import com.ubuuy.springserver.services.UserService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserServiceModel saveOne(UserServiceModel userServiceModel) {
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
                        .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND_IN_DATABASE_BY_EMAIL));

        return mapper.toModel(userEntity, UserServiceModel.class);

    }

    @Override
    @Transactional
    public UserServiceModel registerOrganizationOwner(RegisterOwnerRequest registerOwnerRequest) {

        OrganizationServiceModel organizationServiceModel =
                new OrganizationServiceModel().setName(registerOwnerRequest.getOrganization());

        UserEntity userEntity = new UserEntity()
                .setEmail(registerOwnerRequest.getEmail())
                .setFullName(registerOwnerRequest.getFullName())
                .setPassword(passwordEncoder.encode(registerOwnerRequest.getPassword()))
                .setRoles(List.of(new RoleEntity().setRole(UserRole.OWNER)))
                .setMeta(new Meta(registerOwnerRequest.getEmail()))
                .setOrganization(mapper.toModel(organizationServiceModel, OrganizationEntity.class));


        return mapper.toModel(userRepository.saveAndFlush(userEntity), UserServiceModel.class);
    }
}
