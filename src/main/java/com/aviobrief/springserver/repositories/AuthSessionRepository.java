package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.auth.AuthSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthSessionRepository extends JpaRepository<AuthSession, Long> {
}
