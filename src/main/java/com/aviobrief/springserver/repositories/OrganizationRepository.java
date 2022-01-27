package com.aviobrief.springserver.repositories;

import com.aviobrief.springserver.models.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}
