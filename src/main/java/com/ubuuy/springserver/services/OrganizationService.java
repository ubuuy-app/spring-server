package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.AddPurchaseAndProductRequest;
import com.ubuuy.springserver.models.responses.api_responses.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.responses.view_models.PurchaseViewModel;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public interface OrganizationService {
    OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel);

    OrganizationServiceModel findById(Long id) throws NoSuchElementException;

    AddProductAndPurchaseResponse addNewPurchaseAndProduct(AddPurchaseAndProductRequest addPurchaseAndProductRequest,
                                                           Long organizationId) throws SQLException;

    PurchaseViewModel addNewPurchaseAndProductFullDataResponse(AddPurchaseAndProductRequest addPurchaseAndProductRequest,
                                                               Long organizationId) throws SQLException;

    List<PurchaseViewModel> getOrganizationPurchases(Long organizationId) throws SQLException;

    List<ProductServiceModel> getOrganizationProducts(Long organizationId) throws SQLException;


}
