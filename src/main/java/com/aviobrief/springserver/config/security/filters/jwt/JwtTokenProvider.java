package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.config.security.speing_security_user_service.SpringSecurityUserDetailsService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final SpringSecurityUserDetailsService springSecurityUserDetailsService;

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-mills}")
    private int jwtExpirationInMs;

    public JwtTokenProvider(SpringSecurityUserDetailsService springSecurityUserDetailsService) {
        this.springSecurityUserDetailsService = springSecurityUserDetailsService;
    }

    public String generateToken(String email) {

        UserDetails userDetails = springSecurityUserDetailsService.loadUserByUsername(email);

        Instant now = Instant.now();
        Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, convertToBites(jwtSecretKey))
                .compact();
    }

    public String getUserEmailFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(convertToBites(jwtSecretKey))
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {

        try {
            Jwts.parser()
                    .setSigningKey(convertToBites(jwtSecretKey))
                    .parseClaimsJws(authToken);

            return true;

        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }

        return false;
    }

    private byte[] convertToBites(String key) {
        return DatatypeConverter.parseBase64Binary(key);
    }


}
