package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import com.aviobrief.springserver.models.auth.AuthSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthMetadataRepository extends JpaRepository<AuthMetadata, Long> {

    AuthMetadata findFirstByDeviceDetailsAndLocation(String deviceDetails, String location);
    AuthMetadata findFirstByDeviceDetails(String deviceDetails);

    @Modifying
    @Query("update AuthMetadata am set am.authSessions = :authSessions where am.id = :id")
    void addSession(@Param(value = "id") long id, @Param(value = "authSessions") List<AuthSession> authSessions);
}
