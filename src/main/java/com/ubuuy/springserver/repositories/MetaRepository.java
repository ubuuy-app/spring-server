package com.ubuuy.springserver.repositories;

import com.ubuuy.springserver.models.entities.MetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<MetaEntity, Long> {
}
