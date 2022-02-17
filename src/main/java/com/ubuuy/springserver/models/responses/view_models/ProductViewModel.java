package com.ubuuy.springserver.models.responses.view_models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductViewModel {

    private Long id;
    private String image;
    private String productName;
    private Double price;

    public ProductViewModel() {
    }

    @JsonProperty("_id")
    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductViewModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductViewModel setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductViewModel setPrice(Double price) {
        this.price = price;
        return this;
    }
}
