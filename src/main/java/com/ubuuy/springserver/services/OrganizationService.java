package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public interface OrganizationService {
    OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel);

    OrganizationServiceModel findById(Long id) throws NoSuchElementException;

    AddProductAndPurchaseResponse addNewPurchaseAndProduct(AddProductRequest addProductRequest,
                                                           Long organizationId) throws SQLException;
}
