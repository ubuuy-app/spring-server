package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMetadataRepository extends JpaRepository<AuthMetadata, Long> {
}
