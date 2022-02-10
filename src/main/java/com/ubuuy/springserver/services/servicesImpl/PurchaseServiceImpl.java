package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.entities.PurchaseEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;
import com.ubuuy.springserver.models.enums.ProductPackage;
import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;
import com.ubuuy.springserver.repositories.PurchaseRepository;
import com.ubuuy.springserver.services.MetaService;
import com.ubuuy.springserver.services.PurchaseService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final MetaService metaService;
    private final Mapper mapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, MetaService metaService, Mapper mapper) {
        this.purchaseRepository = purchaseRepository;
        this.metaService = metaService;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public AddProductAndPurchaseResponse saveNewPurchaseAndProduct(AddProductRequest addProductRequest) throws SQLException {

        try {
            MetaEntity newItemMeta = this.metaService.createMetaData(MetaActionEnum.CREATE);

            ProductServiceModel productServiceModel =
                    new ProductServiceModel()
                            .setProductName(addProductRequest.getProductName())
                            .setImage(addProductRequest.getImage())
                            .setPrice(0.0)
                            .setMetaEntity(newItemMeta);

            PurchaseEntity purchaseEntityToSave =
                    new PurchaseEntity()
                            .setProduct(mapper.toModel(productServiceModel, ProductEntity.class))
                            .setQuantity(1)
                            .setProductPackage(ProductPackage.NOT_SPECIFIED)
                            .setPriority(addProductRequest.getPriority())
                            .setStore(null)
                            .setExactBrand(addProductRequest.getExactBrand())
                            .setMetaEntity(newItemMeta);

            PurchaseEntity purchaseEntity =
                    this.purchaseRepository.save(purchaseEntityToSave);

            return new AddProductAndPurchaseResponse()
                    .setProductId(purchaseEntity.getProduct().getId())
                    .setPurchaseId(purchaseEntity.getId());

        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }
}
