package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.entities.PurchaseEntity;
import com.ubuuy.springserver.models.enums.ProductPackage;
import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;
import com.ubuuy.springserver.repositories.OrganizationRepository;
import com.ubuuy.springserver.services.MetaService;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.services.ProductService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ProductService productService;
    private final MetaService metaService;
    private final Mapper mapper;


    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   ProductService productService, MetaService metaService,
                                   Mapper mapper) {
        this.organizationRepository = organizationRepository;
        this.productService = productService;
        this.metaService = metaService;
        this.mapper = mapper;
    }

    @Override
    public OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel) {

        organizationServiceModel.setMetaEntity(metaService.create());

        OrganizationEntity organizationEntity =
                organizationRepository.saveAndFlush(mapper.toModel(organizationServiceModel, OrganizationEntity.class));

        return mapper.toModel(organizationEntity, OrganizationServiceModel.class);
    }

    @Override
    public OrganizationServiceModel findById(Long id) throws NoSuchElementException {

        OrganizationEntity organizationEntity
                = this.organizationRepository.findById(id).orElseThrow(NoSuchElementException::new);

        return mapper.toModel(organizationEntity, OrganizationServiceModel.class);
    }

    @Override
    @Transactional
    public AddProductAndPurchaseResponse addNewPurchaseAndProduct(AddProductRequest addProductRequest,
                                                                  Long organizationId) throws SQLException {

        try {

            OrganizationEntity organizationInDb = this.organizationRepository.getById(organizationId);

            ProductEntity productEntity =
                    new ProductEntity()
                            .setProductName(addProductRequest.getProductName())
                            .setImage(addProductRequest.getImage())
                            .setPrice(0.0)
                            .setMetaEntity(metaService.create());

            PurchaseEntity purchaseEntity =
                    new PurchaseEntity()
                            .setMetaEntity(metaService.create())
                            .setProduct(productEntity)
                            .setQuantity(1)
                            .setProductPackage(ProductPackage.NOT_SPECIFIED)
                            .setPriority(addProductRequest.getPriority())
                            .setStore(null)
                            .setExactBrand(addProductRequest.getExactBrand())
                            .setBought(false);

            organizationInDb.getPurchases().add(purchaseEntity);
            organizationInDb.getProducts().add(productEntity);

            OrganizationEntity updatedOrganizationInDb =
                    this.organizationRepository.save(organizationInDb);


            return new AddProductAndPurchaseResponse()
                    .setOrganizationId(organizationId)
                    .setProductId(updatedOrganizationInDb.getProducts().get(updatedOrganizationInDb.getProducts().size() - 1).getId())
                    .setPurchaseId(updatedOrganizationInDb.getPurchases().get(updatedOrganizationInDb.getPurchases().size() - 1).getId());

        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }

    @Override
    public CompletableFuture<List<ProductServiceModel>> getOrganizationProducts(Long organizationId) throws SQLException {
        OrganizationEntity organization = this.organizationRepository.getById(organizationId);
        return null;
    }


}
