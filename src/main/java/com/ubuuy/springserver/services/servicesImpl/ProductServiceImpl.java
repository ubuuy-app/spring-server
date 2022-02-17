package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;
import com.ubuuy.springserver.repositories.ProductRepository;
import com.ubuuy.springserver.services.ProductService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.ubuuy.springserver.config.constants.ExceptionMessages.ENTITY_DATABASE_FETCH_FAIL;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    @Override
    public CompletableFuture<List<ProductServiceModel>> getAllProducts(Long organizationId) throws SQLException {
        try {
            return CompletableFuture
                    .supplyAsync(() -> mapper.toModel(productRepository.findAll(), ProductServiceModel.class))
                    .orTimeout(ApplicationConstants.COMPLETABLE_AWAIT_TIME_SEC, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new SQLException(ENTITY_DATABASE_FETCH_FAIL);
        }
    }

}
