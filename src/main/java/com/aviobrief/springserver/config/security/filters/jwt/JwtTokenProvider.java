package com.aviobrief.springserver.config.security.filters.jwt;

import com.aviobrief.springserver.config.security.speing_security_user_service.SpringSecurityUserDetailsService;
import com.aviobrief.springserver.utils.logger.ServerLogger;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final ServerLogger serverLogger;
    private final String jwtSecretKey;
    private final int jwtExpirationInMs;
    private final SpringSecurityUserDetailsService springSecurityUserDetailsService;


    public JwtTokenProvider(
            ServerLogger serverLogger,
            @Value("${app.jwt-secret}")
                    String jwtSecretKey,
            @Value("${app.jwt-expiration-mills}")
                    int jwtExpirationInMs,
            SpringSecurityUserDetailsService springSecurityUserDetailsService) {
        this.serverLogger = serverLogger;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
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
            serverLogger.error("JwtTokenProvider", "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            serverLogger.error("JwtTokenProvider", "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            serverLogger.error("JwtTokenProvider", "JWT claims string is empty");
        }

        return false;
    }

    private byte[] convertToBites(String key) {
        return DatatypeConverter.parseBase64Binary(key);
    }


}
