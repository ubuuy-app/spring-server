package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.service_models.ProductServiceModel;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    CompletableFuture<List<ProductServiceModel>> getAllProducts(Long organizationId) throws SQLException;


}
