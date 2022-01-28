package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.auth.AuthMetadata;
import com.ubuuy.springserver.repositories.AuthMetadataRepository;
import com.ubuuy.springserver.services.AuthMetadataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthMetadataServiceImpl implements AuthMetadataService {

    private final AuthMetadataRepository authMetadataRepository;

    public AuthMetadataServiceImpl(AuthMetadataRepository authMetadataRepository) {
        this.authMetadataRepository = authMetadataRepository;
    }

    @Override
    public void save(AuthMetadata authMetadata) {
        this.authMetadataRepository.saveAndFlush(authMetadata);
    }

    @Override
    public List<AuthMetadata> getAllActiveSessionsForCurrentUser() {
        return this.authMetadataRepository.findAllActiveSessionsByUser();
    }

    @Override
    public void saveAll(List<AuthMetadata> activeSessions) {
        this.authMetadataRepository.saveAllAndFlush(activeSessions);
    }

    @Override
    public List<AuthMetadata> getAllActiveSessionsUser(String userEmail) {
        return this.authMetadataRepository.findAllActiveSessionsByUser(userEmail);
    }
}
