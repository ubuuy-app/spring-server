package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.service_models.ProductServiceModel;

import java.sql.SQLException;

public interface ProductService {

    ProductServiceModel save(ProductServiceModel productServiceModel) throws SQLException;
}
