package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.repositories.OrganizationRepository;
import com.ubuuy.springserver.services.MetaService;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MetaService metaService;
    private final Mapper mapper;


    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   MetaService metaService,
                                   Mapper mapper) {
        this.organizationRepository = organizationRepository;
        this.metaService = metaService;
        this.mapper = mapper;
    }

    @Override
    public OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel) {

        organizationServiceModel.setMetaEntity(metaService.create());

        OrganizationEntity organizationEntity =
                organizationRepository.saveAndFlush(mapper.toModel(organizationServiceModel, OrganizationEntity.class));

        return mapper.toModel(organizationEntity, OrganizationServiceModel.class);
    }

    @Override
    public OrganizationServiceModel getById(Long id) throws NoSuchElementException {

        OrganizationEntity organizationEntity
                = this.organizationRepository.findById(id).orElseThrow(NoSuchElementException::new);

        return mapper.toModel(organizationEntity, OrganizationServiceModel.class);
    }
}
