package com.ubuuy.springserver.web;


import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.models.responses.api.AddProductAndPurchaseResponse;
import com.ubuuy.springserver.services.PurchaseService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/organizations/{organizationId}/products")
public class OrganizationProductController {

    private static final Logger logger = Logger.getLogger("OrganizationProductController");
    private final PurchaseService purchaseService;
    private final Mapper mapper;
    private final ResponseBuilder responseBuilder;

    public OrganizationProductController(PurchaseService purchaseService, Mapper mapper, ResponseBuilder responseBuilder) {
        this.purchaseService = purchaseService;
        this.mapper = mapper;
        this.responseBuilder = responseBuilder;
    }

    @PostMapping
    public ResponseEntity<?> addProductToOrganization(
            @PathVariable("organizationId") Long organizationId,
            @RequestBody AddProductRequest addProductRequest) {

        try {
            AddProductAndPurchaseResponse addProductAndPurchaseResponse =
                    purchaseService.saveNewPurchaseAndProduct(addProductRequest);

            return ResponseEntity
                    .ok()
                    .body(addProductAndPurchaseResponse);

        } catch (Exception ex) {
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.ADD_PRODUCT)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(null))
                    ;
        }

    }
}
