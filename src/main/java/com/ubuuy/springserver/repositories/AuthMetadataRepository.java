package com.ubuuy.springserver.repositories;

import com.ubuuy.springserver.models.auth.AuthMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthMetadataRepository extends JpaRepository<AuthMetadata, Long> {

    @Query("select am from AuthMetadata am where am.userEntity.email = :#{principal.username} and am.logout is null and am.jwt is not null ")
    Optional<List<AuthMetadata>> findAllActiveSessionsByUser();

    @Query("select am from AuthMetadata am where am.userEntity.email = :userEmail and am.logout is null and am.jwt is not null ")
    Optional<List<AuthMetadata>> findAllActiveSessionsByUser(@Param("userEmail") String userEmail);

}
