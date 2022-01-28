package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.repositories.OrganizationRepository;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final Mapper mapper;


    public OrganizationServiceImpl(OrganizationRepository organizationRepository, Mapper mapper) {
        this.organizationRepository = organizationRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(OrganizationServiceModel organizationServiceModel){
        this
                .organizationRepository
                .saveAndFlush(mapper.toModel(organizationServiceModel, OrganizationEntity.class));
    }
}
