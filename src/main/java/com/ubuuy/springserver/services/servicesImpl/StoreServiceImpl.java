package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ApplicationConstants;
import com.ubuuy.springserver.models.entities.StoreEntity;
import com.ubuuy.springserver.models.requests.AddStoreRequest;
import com.ubuuy.springserver.models.responses.view_models.StoreViewModel;
import com.ubuuy.springserver.models.service_models.StoreServiceModel;
import com.ubuuy.springserver.repositories.StoreRepository;
import com.ubuuy.springserver.services.StoreService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.ubuuy.springserver.config.constants.ExceptionMessages.ENTITY_DATABASE_FETCH_FAIL;
import static com.ubuuy.springserver.config.constants.ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final Mapper mapper;

    public StoreServiceImpl(StoreRepository storeRepository, Mapper mapper) {
        this.storeRepository = storeRepository;
        this.mapper = mapper;
    }

    @Override
    public CompletableFuture<List<StoreViewModel>> getAll() throws SQLException {
        try {
            return CompletableFuture
                    .supplyAsync(() -> mapper.toModel(storeRepository.findAll(), StoreViewModel.class))
                    .orTimeout(ApplicationConstants.COMPLETABLE_AWAIT_TIME_SEC, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new SQLException(ENTITY_DATABASE_FETCH_FAIL);
        }
    }

    @Override
    public StoreServiceModel findByNameOrAddNew(String name) throws SQLException {
        try {
            StoreEntity storeEntity = this
                    .storeRepository
                    .findByName(name)
                    .orElse(new StoreEntity(name));

            return mapper.toModel(storeEntity, StoreServiceModel.class);

        } catch (Exception e) {
            throw new SQLException(ENTITY_DATABASE_FETCH_FAIL);
        }
    }

    @Override
    public StoreViewModel addNewStore(AddStoreRequest addStoreRequest) throws SQLException {
        try {
            addStoreRequest.setName(addStoreRequest.getName().toLowerCase(Locale.ROOT));
            StoreEntity storeEntity =
                    this.storeRepository.saveAndFlush(mapper.toModel(addStoreRequest, StoreEntity.class));

            return mapper.toModel(storeEntity, StoreViewModel.class);

        } catch (Exception e) {
            throw new SQLException(ENTITY_DATABASE_SAVE_FAIL);
        }
    }
}
