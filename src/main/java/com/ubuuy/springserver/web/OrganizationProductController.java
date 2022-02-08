package com.ubuuy.springserver.web;


import com.ubuuy.springserver.models.requests.AddProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations/{organizationId}/products")
public class OrganizationProductController {

    @PostMapping
    public ResponseEntity<?> addProductToOrganization(
            @PathVariable("organizationId") Long organizationId,
            @RequestBody AddProductRequest addProductRequest) {

        System.out.println(6);


        return null;
    }
}
