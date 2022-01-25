package com.aviobrief.springserver.services;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.auth.AuthSession;

import java.util.List;

public interface AuthSessionService {

    List<AuthSession> getAllActiveSessionsForAuthMetadata(AuthMetadata authMetadata);
}
