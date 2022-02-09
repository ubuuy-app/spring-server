package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;
import com.ubuuy.springserver.repositories.ProductRepository;
import com.ubuuy.springserver.services.ProductService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Mapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, Mapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductServiceModel save(ProductServiceModel productServiceModel) throws SQLException {

        try {
            ProductEntity productEntity =
                    this.productRepository
                            .save(mapper.toModel(productServiceModel, ProductEntity.class));

            return  mapper.toModel(productEntity, ProductServiceModel.class);
        } catch (Exception ex){
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }
}
