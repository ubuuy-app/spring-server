package com.ubuuy.springserver.web;

import com.ubuuy.springserver.models.requests.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @PostMapping(path = "/api/products")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest addProductRequest) {

        System.out.println(6);


        return null;
    }
}
