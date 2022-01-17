package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.config.security.speing_security_user_service.SpringSecurityUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;


    @Mock
    private SpringSecurityUserDetailsService securityUserDetailsService;


    @BeforeEach
    private void initJwtTokenProvider() {
    }

    @Test
    void generateToken_works_ok() {
        UserDetails testUserDetails = new User(
                "test@test.com",
                "testPassword",
                List.of(new SimpleGrantedAuthority("testRole")));

        when(securityUserDetailsService.loadUserByUsername("john.doe@icloud.com")).thenReturn(testUserDetails);

        String testJwtToken = jwtTokenProvider.generateToken("john.doe@icloud.com");
        System.out.println(testJwtToken);
    }
}