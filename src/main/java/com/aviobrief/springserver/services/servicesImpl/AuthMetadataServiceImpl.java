package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.repositories.AuthMetadataRepository;
import com.aviobrief.springserver.services.AuthMetadataService;
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
}
