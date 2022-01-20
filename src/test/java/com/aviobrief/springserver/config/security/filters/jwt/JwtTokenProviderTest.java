package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.config.security.jwt.JwtTokenProvider;
import com.aviobrief.springserver.config.security.spring_security_user_service.SpringSecurityUserDetailsService;
import com.aviobrief.springserver.utils.logger.ServerLogger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private String jwtSecretKey = "mySuperSecretTestKey";
    private int jwtExpirationInMs = 5000;
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ServerLogger serverLogger;
    @Mock
    private SpringSecurityUserDetailsService securityUserDetailsService;

    @BeforeEach
    private void initJwtTokenProvider() {
        UserDetails testUserDetails =
                new User("test@test.io", "testPass", List.of(new SimpleGrantedAuthority("testRole")));
        when(securityUserDetailsService.loadUserByUsername("john.doe@icloud.com")).thenReturn(testUserDetails);

        jwtTokenProvider = new JwtTokenProvider(serverLogger, jwtSecretKey, jwtExpirationInMs, securityUserDetailsService);
    }

    @Test
    void generateToken_works_ok() {
        String testJwtToken = jwtTokenProvider.generateToken("john.doe@icloud.com");
        String emailFromJwt =  jwtTokenProvider.getUserEmailFromJWT(testJwtToken);

        assertThat(jwtTokenProvider.validateToken(testJwtToken)).isEqualTo(true);
        assertThat(emailFromJwt).isEqualTo("test@test.io");
        verify(serverLogger, times(0)).error("","");
    }

    @Test
    void generateToken_logs_Invalid_JWT_signature() {

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("john.doe@icloud.com");
        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        String wrongSignatureJWT = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("wrongTestSuperSecretKey"))
                .compact();

        assertThat(jwtTokenProvider.validateToken(wrongSignatureJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider","Invalid JWT signature");
    }

    @Test
    void generateToken_logs_Invalid_JWT_token() {
        String testJwtToken = jwtTokenProvider.generateToken("john.doe@icloud.com");
        String tamperedJwt = testJwtToken.replaceAll("e","");

        assertThat(jwtTokenProvider.validateToken(tamperedJwt)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider","Invalid JWT token");
    }

    @Test
    void generateToken_logs_Expired_JWT_token() throws InterruptedException {
        String testJwtToken = jwtTokenProvider.generateToken("john.doe@icloud.com");

        Thread.sleep(5001);
        assertThat(jwtTokenProvider.validateToken(testJwtToken)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider","Expired JWT token");
    }

    @Test
    void generateToken_logs_Unsupported_JWT_token() {

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername("john.doe@icloud.com");
        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        String unsignedJWT = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .compact();

        assertThat(jwtTokenProvider.validateToken(unsignedJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider","Unsupported JWT token");
    }

    @Test
    void generateToken_logs_JWT_claims_string_is_empty() {
        String testJwtToken = jwtTokenProvider.generateToken("john.doe@icloud.com");
        String emptyJWT = "";

        assertThat(jwtTokenProvider.validateToken(emptyJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider","JWT claims string is empty");
    }



}