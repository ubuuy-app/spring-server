package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.auth.AuthSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthSessionRepository extends JpaRepository<AuthSession, Long> {

    @Query("select asn from AuthSession asn where asn.authMetadata = :authMetadata and asn.logout is null ")
    List<AuthSession> findAllActiveSessions(
            @Param(value = "authMetadata") AuthMetadata authMetadata);

}
