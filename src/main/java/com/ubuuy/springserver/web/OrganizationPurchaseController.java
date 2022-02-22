package com.ubuuy.springserver.web;


import com.ubuuy.springserver.models.requests.AddPurchaseAndProductRequest;
import com.ubuuy.springserver.models.responses.view_models.PurchaseViewModel;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.services.PurchaseService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/organizations/{organizationId}/purchases")
public class OrganizationPurchaseController {

    private static final Logger logger = Logger.getLogger("OrganizationPurchaseController");
    private final OrganizationService organizationService;
    private final PurchaseService purchaseService;
    private final Mapper mapper;
    private final ResponseBuilder responseBuilder;

    public OrganizationPurchaseController(
            OrganizationService organizationService, PurchaseService purchaseService, Mapper mapper, ResponseBuilder responseBuilder) {
        this.organizationService = organizationService;
        this.purchaseService = purchaseService;
        this.mapper = mapper;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping
    public ResponseEntity<?> addPurchaseToOrganization(
            @PathVariable("organizationId") Long organizationId,
            @RequestBody AddPurchaseAndProductRequest addPurchaseAndProductRequest) {

        try {
            PurchaseViewModel purchaseViewModel =
                    organizationService
                            .addNewPurchaseAndProductFullDataResponse(addPurchaseAndProductRequest, organizationId);

            return ResponseEntity
                    .ok()
                    .body(purchaseViewModel);

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.PURCHASES)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }

    }

    @GetMapping
    ResponseEntity<?> getOrganizationPurchases(@PathVariable("organizationId") Long organizationId) {

        try {
            List<PurchaseViewModel> purchaseViewModelList =
                    organizationService.getOrganizationPurchases(organizationId);

            return ResponseEntity
                    .ok()
                    .body(purchaseViewModelList);

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.PURCHASES)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }
    }

    @PatchMapping("/{purchaseId}/")
    ResponseEntity<?> organizationPurchaseAction(
            @PathVariable("organizationId") Long organizationId,
            @PathVariable("purchaseId") Long purchaseId,
            @RequestParam("action") String action) {

        try {
            PurchaseViewModel purchaseViewModel =
                    this.purchaseService.doPurchaseAction(organizationId, purchaseId, action);

            return ResponseEntity
                    .ok()
                    .body(purchaseViewModel);

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.PURCHASES)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }
    }


}
