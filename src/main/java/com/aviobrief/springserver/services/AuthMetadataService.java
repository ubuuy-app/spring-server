package com.aviobrief.springserver.services;

import com.aviobrief.springserver.models.auth.AuthMetadata;

import java.util.List;

public interface AuthMetadataService {

    void save(AuthMetadata authMetadata);

    List<AuthMetadata> getAllActiveSessionsForCurrentUser();
}
