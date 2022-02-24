package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.requests.AddStoreRequest;
import com.ubuuy.springserver.models.responses.view_models.StoreViewModel;
import com.ubuuy.springserver.models.service_models.StoreServiceModel;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StoreService {

    CompletableFuture<List<StoreViewModel>> getAll() throws SQLException;

    StoreServiceModel findByNameOrAddNew(String name) throws SQLException;

    StoreViewModel addNewStore(AddStoreRequest addStoreRequest) throws SQLException;
}
