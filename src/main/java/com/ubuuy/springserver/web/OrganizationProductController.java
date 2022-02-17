package com.ubuuy.springserver.web;

import com.ubuuy.springserver.models.responses.view_models.ProductViewModel;
import com.ubuuy.springserver.services.OrganizationService;
import com.ubuuy.springserver.utils.mapper.Mapper;
import com.ubuuy.springserver.utils.response_builder.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/organizations/{organizationId}/products")
public class OrganizationProductController {

    private static final Logger logger = Logger.getLogger("OrganizationProductController");
    private final OrganizationService organizationService;
    private final Mapper mapper;
    private final ResponseBuilder responseBuilder;

    public OrganizationProductController(OrganizationService organizationService,
                                         Mapper mapper,
                                         ResponseBuilder responseBuilder) {
        this.organizationService = organizationService;
        this.mapper = mapper;
        this.responseBuilder = responseBuilder;
    }


    @GetMapping
    ResponseEntity<?> getOrganizationProducts(@PathVariable("organizationId") Long organizationId) {

        try {
            List<ProductViewModel> productViewModelList =
                    mapper.toModel(organizationService
                            .getOrganizationProducts(organizationId), ProductViewModel.class);

            return ResponseEntity
                    .ok()
                    .body(productViewModelList);

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.ADD_PRODUCT)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }
    }
}
