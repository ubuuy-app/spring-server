package com.ubuuy.springserver.web;

import com.ubuuy.springserver.models.requests.AddProductRequest;
import com.ubuuy.springserver.services.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = Logger.getLogger("ProductController");
    private final ProductService productService;
    private final Mapper mapper;
    private final ResponseBuilder responseBuilder;

    public ProductController(ProductService productService, Mapper mapper, ResponseBuilder responseBuilder) {
        this.productService = productService;
        this.mapper = mapper;
        this.responseBuilder = responseBuilder;
    }

    @GetMapping(path = "/packages")
    ResponseEntity<?> getAvailableProductPackages() {

        try {
            List<String> productPackages = productService.getProductPackages();

            return ResponseEntity
                    .ok()
                    .body(productPackages);

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return ResponseEntity
                    .badRequest() //todo - revise message or implement ErrorBuilder via method or interceptor
                    .body(responseBuilder
                            .buildErrorObject(true)
                            .setType(ResponseBuilder.Type.PRODUCTS)
                            .setStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .setMessage("Server could not process the request")
                            .setErrors(new ArrayList<>()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest addProductRequest) {

        System.out.println(6);


        return null;
    }
}
