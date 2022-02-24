package com.ubuuy.springserver.web;


import com.ubuuy.springserver.config.constants.LoggerMessages;
import com.ubuuy.springserver.models.requests.AddStoreRequest;
import com.ubuuy.springserver.models.responses.api_responses.GenericIdResponse;
import com.ubuuy.springserver.models.responses.view_models.StoreViewModel;
import com.ubuuy.springserver.services.StoreService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private static final Logger logger = Logger.getLogger("StoreController");
    private final StoreService storeService;
    private final Mapper mapper;
    private final ResponseBuilder responseBuilder;

    public StoreController(StoreService storeService, Mapper mapper, ResponseBuilder responseBuilder) {
        this.storeService = storeService;
        this.mapper = mapper;
        this.responseBuilder = responseBuilder;
    }

    @GetMapping
    public DeferredResult<ResponseEntity<List<StoreViewModel>>> getAll() {
        DeferredResult<ResponseEntity<List<StoreViewModel>>> dr = new DeferredResult<>();

        try {
            storeService
                    .getAll()
                    .thenApply(storeServiceModels -> {

                        logger.log(Level.INFO, LoggerMessages.STORES_GET_ALL_OK);
                        return dr.setResult(
                                ResponseEntity
                                        .ok()
                                        .body(mapper.toModel(storeServiceModels, StoreViewModel.class))
                        );
                    })
                    .exceptionally(e -> {
                        System.out.println(e);
                        logger.log(Level.INFO, LoggerMessages.STORES_GET_ALL_FAIL);
                        return dr.setResult(ResponseEntity
                                .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                                .build());
                    });
            return dr;
        } catch (Exception e) {
            logger.log(Level.INFO, LoggerMessages.STORES_GET_ALL_FAIL);
            dr.setResult(ResponseEntity
                    .notFound() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .build());
            return dr;
        }
    }

    @PostMapping
    public ResponseEntity<?> addStore(@RequestBody AddStoreRequest addStoreRequest) {

        try {
            StoreViewModel storeViewModel = storeService.addNewStore(addStoreRequest);

            return ResponseEntity
                    .ok()
                    .body(new GenericIdResponse().setId(storeViewModel.getId()));

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.STORES)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }
    }
}
