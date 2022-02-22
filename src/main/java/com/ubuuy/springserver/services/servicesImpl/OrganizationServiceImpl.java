package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.OrganizationEntity;
import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.entities.PurchaseEntity;
import com.ubuuy.springserver.models.requests.AddPurchaseAndProductRequest;
import com.ubuuy.springserver.models.responses.api_responses.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.models.responses.view_models.PurchaseViewModel;
import com.ubuuy.springserver.models.service_models.OrganizationServiceModel;
import com.ubuuy.springserver.models.service_models.ProductServiceModel;
import com.ubuuy.springserver.repositories.OrganizationRepository;
import com.ubuuy.springserver.services.MetaService;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.services.ProductService;
import com.ubuuy.springserver.services.PurchaseService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final MetaService metaService;
    private final Mapper mapper;


    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   ProductService productService, PurchaseService purchaseService, MetaService metaService,
                                   Mapper mapper) {
        this.organizationRepository = organizationRepository;
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.metaService = metaService;
        this.mapper = mapper;
    }

    @Override
    public OrganizationServiceModel saveNewOrganization(OrganizationServiceModel organizationServiceModel) {

        organizationServiceModel.setMetaData(metaService.create());

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
    public AddProductAndPurchaseResponse addNewPurchaseAndProduct(AddPurchaseAndProductRequest addPurchaseAndProductRequest,
                                                                  Long organizationId) throws SQLException {

        try {

            OrganizationEntity organizationInDb = this.organizationRepository.getById(organizationId);

            ProductEntity productEntity =
                    new ProductEntity()
                            .setMetaData(metaService.create())
                            .setProductName(addPurchaseAndProductRequest.getProductName())
                            .setImage(addPurchaseAndProductRequest.getImage())
                            .setPrice(0.0)
                            .setProductPackage(addPurchaseAndProductRequest.getProductPackage());

            PurchaseEntity purchaseEntity =
                    new PurchaseEntity()
                            .setMetaData(metaService.create())
                            .setProduct(productEntity)
                            .setQuantity(1)
                            .setPriority(addPurchaseAndProductRequest.getPriority())
                            .setStore(null)
                            .setExactBrand(addPurchaseAndProductRequest.getExactBrand())
                            .setBought(false)
                            .setQuantity(addPurchaseAndProductRequest.getQuantity());

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
    @Transactional
    public PurchaseViewModel addNewPurchaseAndProductFullDataResponse(AddPurchaseAndProductRequest addPurchaseAndProductRequest, Long organizationId) throws SQLException {
        try {

            OrganizationEntity organizationInDb = this.organizationRepository.getById(organizationId);

            ProductEntity productEntity =
                    new ProductEntity()
                            .setMetaData(metaService.create())
                            .setProductName(addPurchaseAndProductRequest.getProductName())
                            .setImage(addPurchaseAndProductRequest.getImage())
                            .setPrice(0.0)
                            .setProductPackage(addPurchaseAndProductRequest.getProductPackage());

            PurchaseEntity purchaseEntity =
                    new PurchaseEntity()
                            .setMetaData(metaService.create())
                            .setProduct(productEntity)
                            .setQuantity(1)
                            .setPriority(addPurchaseAndProductRequest.getPriority())
                            .setStore(null)
                            .setExactBrand(addPurchaseAndProductRequest.getExactBrand())
                            .setBought(false)
                            .setQuantity(addPurchaseAndProductRequest.getQuantity());

            organizationInDb.getPurchases().add(purchaseEntity);
            organizationInDb.getProducts().add(productEntity);

            this.organizationRepository.save(organizationInDb);

            OrganizationEntity savedOrganization = this.organizationRepository.getById(organizationId);
            PurchaseEntity savedPurchase
                    = savedOrganization.getPurchases().get(savedOrganization.getPurchases().size() - 1);

            return mapper.toModel(savedPurchase, PurchaseViewModel.class);

        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }

    @Override
    @Transactional
    public List<PurchaseViewModel> getOrganizationPurchases(Long organizationId) throws SQLException {
        try {
            OrganizationEntity organization =
                    this.organizationRepository
                            .findById(organizationId)
                            .orElseThrow(NoSuchElementException::new);

            List<PurchaseViewModel> purchaseViewModelList =
                    mapper.toModel(organization.getPurchases(), PurchaseViewModel.class);

            return purchaseViewModelList;
        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_FETCH_FAIL);
        }
    }

    @Override
    @Transactional
    public List<ProductServiceModel> getOrganizationProducts(Long organizationId) throws SQLException {
        try {
            OrganizationEntity organization =
                    this.organizationRepository
                            .findById(organizationId)
                            .orElseThrow(NoSuchElementException::new);

            return mapper.toModel(organization.getProducts(), ProductServiceModel.class);
        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_FETCH_FAIL);
        }
    }


}
