package com.aviobrief.springserver.services;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.auth.AuthSession;
import com.aviobrief.springserver.repositories.AuthSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthSessionServiceImpl implements AuthSessionService {

    private final AuthSessionRepository authSessionRepository;

    public AuthSessionServiceImpl(AuthSessionRepository authSessionRepository) {
        this.authSessionRepository = authSessionRepository;
    }

    @Override
    public List<AuthSession> getAllActiveSessionsForAuthMetadata(AuthMetadata authMetadata) {
        return this.authSessionRepository.findAllActiveSessions(authMetadata);
    }
}
