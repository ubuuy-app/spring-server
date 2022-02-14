package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.meta_data.AuthMetadata;

import java.util.List;

public interface AuthMetadataService {

    void save(AuthMetadata authMetadata);

    List<AuthMetadata> getAllActiveSessionsForCurrentUser();

    void saveAll(List<AuthMetadata> activeSessions);

    List<AuthMetadata> getAllActiveSessionsUser(String userEmail);
}
