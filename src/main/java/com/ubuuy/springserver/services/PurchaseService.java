package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.responses.view_models.PurchaseViewModel;
import com.ubuuy.springserver.models.service_models.PurchaseServiceModel;

import java.sql.SQLException;

public interface PurchaseService {

    PurchaseServiceModel findById(Long purchaseId) throws SQLException;

    PurchaseViewModel doPurchaseAction(Long organizationId, Long purchaseId, String action) throws SQLException;


}
