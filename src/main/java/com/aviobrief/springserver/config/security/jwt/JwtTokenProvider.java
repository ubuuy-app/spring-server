package com.aviobrief.springserver.config.security.jwt;

import com.aviobrief.springserver.services.servicesImpl.UserDetailsSpringService;
import com.aviobrief.springserver.utils.logger.ServerLogger;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Date;

import static com.aviobrief.springserver.config.constants.ApplicationConstants.HTTP_REQ_AUTH_HEADER;
import static com.aviobrief.springserver.config.constants.ApplicationConstants.HTTP_REQ_AUTH_TOKEN_PREFIX;

@Component
public class JwtTokenProvider {

    private final ServerLogger serverLogger;
    private final String jwtSecretKey;
    private final int jwtExpirationInMs;
    private final UserDetailsSpringService userDetailsSpringService;


    public JwtTokenProvider(
            ServerLogger serverLogger,
            @Value("${app.jwt-secret}")
                    String jwtSecretKey,
            @Value("${app.jwt-expiration-mills}")
                    int jwtExpirationInMs,
            UserDetailsSpringService userDetailsSpringService) {
        this.serverLogger = serverLogger;
        this.jwtSecretKey = jwtSecretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.userDetailsSpringService = userDetailsSpringService;
    }

    public String generateToken(String email) {

        UserDetails userDetails = userDetailsSpringService.loadUserByUsername(email);

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

    public String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(HTTP_REQ_AUTH_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HTTP_REQ_AUTH_TOKEN_PREFIX)) {
            String jwt = bearerToken.substring(7, bearerToken.length());
            return jwt.equals("null") ? null : jwt;
        }
        return null;
    }

    private byte[] convertToBites(String key) {
        return DatatypeConverter.parseBase64Binary(key);
    }


}
