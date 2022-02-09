package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;

import java.util.NoSuchElementException;

public interface OrganizationService {
    OrganizationServiceModel save(OrganizationServiceModel organizationServiceModel);

    OrganizationServiceModel getById(Long id) throws NoSuchElementException;
}
