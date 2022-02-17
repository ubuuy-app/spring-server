package com.ubuuy.springserver.repositories;

import com.ubuuy.springserver.models.meta_data.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<MetaData, Long> {
}
