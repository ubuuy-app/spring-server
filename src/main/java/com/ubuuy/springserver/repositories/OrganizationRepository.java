package com.ubuuy.springserver.repositories;

import com.ubuuy.springserver.models.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}
