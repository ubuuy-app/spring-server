package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public interface OrganizationService {
    OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel);

    OrganizationServiceModel findById(Long id) throws NoSuchElementException;

    AddProductAndPurchaseResponse addNewPurchaseAndProduct(AddProductRequest addProductRequest,
                                                           Long organizationId) throws SQLException;

    CompletableFuture<List<ProductServiceModel>> getOrganizationProducts(Long organizationId) throws SQLException;


}
