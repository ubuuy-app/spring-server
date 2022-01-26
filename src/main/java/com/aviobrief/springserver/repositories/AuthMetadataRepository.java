package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.auth.AuthMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthMetadataRepository extends JpaRepository<AuthMetadata, Long> {

    @Query("select am from AuthMetadata am where am.userEntity.email = :#{principal.username} and am.logout is null and am.jwt is not null ")
    List<AuthMetadata> findAllActiveSessionsByUser();
}
