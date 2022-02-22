package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.PurchaseEntity;
import com.ubuuy.springserver.models.responses.view_models.PurchaseViewModel;
import com.ubuuy.springserver.models.service_models.PurchaseServiceModel;
import com.ubuuy.springserver.repositories.PurchaseRepository;
import com.ubuuy.springserver.services.PurchaseService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Service
public class PurchaseServiceImpl implements PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final Mapper mapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, Mapper mapper) {
        this.purchaseRepository = purchaseRepository;
        this.mapper = mapper;
    }


    @Override
    public PurchaseServiceModel findById(Long purchaseId) throws SQLException {
        try {
            return mapper.toModel(this.purchaseRepository.findById(purchaseId), PurchaseServiceModel.class);
        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_FETCH_FAIL);
        }
    }

    @Override
    public PurchaseViewModel doPurchaseAction(Long organizationId, Long purchaseId, String action) throws SQLException {
        try {
            PurchaseEntity purchaseEntity =
                    this.purchaseRepository.findById(purchaseId)
                            .orElseThrow(() -> new NoSuchElementException("Purchase not found by id!"));

            switch (action) {
                case null -> throw new IllegalArgumentException("Action cannot be null!");
                case "buy" -> purchaseEntity.setBought(true);
                case "reject" -> purchaseEntity.setBought(false);
                default -> throw new IllegalArgumentException("Unrecognized action!");
            }

            PurchaseEntity savedPurchase = this.purchaseRepository.saveAndFlush(purchaseEntity);

            return mapper.toModel(savedPurchase, PurchaseViewModel.class);
        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }

}
