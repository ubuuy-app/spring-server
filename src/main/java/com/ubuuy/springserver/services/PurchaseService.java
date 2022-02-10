package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;

import java.sql.SQLException;

public interface PurchaseService {

    AddProductAndPurchaseResponse saveNewPurchaseAndProduct(AddProductRequest addProductRequest) throws SQLException;

}
