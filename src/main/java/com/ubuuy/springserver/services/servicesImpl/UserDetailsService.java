package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.entities.UserEntity;
import com.ubuuy.springserver.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ubuuy.springserver.config.constants.ExceptionMessages.USER_FIND_BY_EMAIL_FAIL;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_FIND_BY_EMAIL_FAIL));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {

        List<GrantedAuthority> authorities = userEntity
                .getRoles()
                .stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
                .collect(Collectors.toList());


        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                authorities
        );
    }
}
