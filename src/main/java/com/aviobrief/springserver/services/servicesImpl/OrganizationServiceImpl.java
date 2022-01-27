package com.aviobrief.springserver.services.servicesImpl;

import com.aviobrief.springserver.models.entities.OrganizationEntity;
import com.aviobrief.springserver.models.service_models.OrganizationServiceModel;
import com.aviobrief.springserver.repositories.OrganizationRepository;
import com.aviobrief.springserver.services.OrganizationService;
import com.aviobrief.springserver.utils.mapper.Mapper;
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
