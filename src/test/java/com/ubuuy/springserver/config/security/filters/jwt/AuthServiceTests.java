package com.ubuuy.springserver.config.security.filters.jwt;

import com.maxmind.geoip2.DatabaseReader;
import com.ubuuy.springserver.repositories.UserRepository;
import com.ubuuy.springserver.services.*;
import com.ubuuy.springserver.services.servicesImpl.AuthServiceImpl;
import com.ubuuy.springserver.services.servicesImpl.UserDetailsService;
import com.ubuuy.springserver.utils.logger.ServerLogger;
import com.ubuuy.springserver.utils.mapper.Mapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import ua_parser.Parser;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @InjectMocks
    private String jwtSecretKey = "mySuperSecretTestKey";
    private int jwtExpirationInMs = 5000;
    private AuthService authService;


    @Mock
    private ServerLogger serverLogger;
    @Mock
    private Mapper mapper;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private UserService userService;
    @Mock
    private OrganizationService organizationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthMetadataService authMetadataService;
    @Mock
    private RoleService roleService;
    @Mock
    private Parser parser;
    @Mock
    private DatabaseReader databaseReader;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MetaService metaService;

    @BeforeEach
    private void initJwtTokenProvider() {
        UserDetails testUserDetails =
                new User("test@test.io", "testPass", List.of(new SimpleGrantedAuthority("testRole")));
        when(userDetailsService.loadUserByUsername("john.doe@icloud.com")).thenReturn(testUserDetails);

        authService = new AuthServiceImpl(
                serverLogger, mapper, jwtSecretKey, jwtExpirationInMs, userRepository, userService, userDetailsService,
                organizationService, authMetadataService, roleService, parser, databaseReader, passwordEncoder, metaService);
    }

    @Test
    void generateToken_works_ok() {
        String testJwtToken = authService.generateJWT("john.doe@icloud.com");
        String emailFromJwt = authService.getUserEmailFromJWT(testJwtToken);

        assertThat(authService.validateJWT(testJwtToken)).isEqualTo(true);
        assertThat(emailFromJwt).isEqualTo("test@test.io");
        verify(serverLogger, times(0)).error("", "");
    }

    @Test
    void generateToken_logs_Invalid_JWT_signature() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("john.doe@icloud.com");
        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        String wrongSignatureJWT = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary("wrongTestSuperSecretKey"))
                .compact();

        assertThat(authService.validateJWT(wrongSignatureJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider", "Invalid JWT signature");
    }

    @Test
    void generateToken_logs_Invalid_JWT_token() {
        String testJwtToken = authService.generateJWT("john.doe@icloud.com");
        String tamperedJwt = testJwtToken.replaceAll("e", "");

        assertThat(authService.validateJWT(tamperedJwt)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider", "Invalid JWT token");
    }

    @Test
    void generateToken_logs_Expired_JWT_token() throws InterruptedException {
        String testJwtToken = authService.generateJWT("john.doe@icloud.com");

        Thread.sleep(5001);
        assertThat(authService.validateJWT(testJwtToken)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider", "Expired JWT token");
    }

    @Test
    void generateToken_logs_Unsupported_JWT_token() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("john.doe@icloud.com");
        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        String unsignedJWT = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .compact();

        assertThat(authService.validateJWT(unsignedJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider", "Unsupported JWT token");
    }

    @Test
    void generateToken_logs_JWT_claims_string_is_empty() {
        String testJwtToken = authService.generateJWT("john.doe@icloud.com");
        String emptyJWT = "";

        assertThat(authService.validateJWT(emptyJWT)).isEqualTo(false);
        verify(serverLogger, times(1)).error("JwtTokenProvider", "JWT claims string is empty");
    }


}