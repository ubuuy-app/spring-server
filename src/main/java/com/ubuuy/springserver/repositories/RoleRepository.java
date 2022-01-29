package com.ubuuy.springserver.repositories;

import com.ubuuy.springserver.models.entities.RoleEntity;
import com.ubuuy.springserver.models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findFirstByRole(UserRole role);
}
