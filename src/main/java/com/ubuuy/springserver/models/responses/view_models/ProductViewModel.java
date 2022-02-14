package com.ubuuy.springserver.models.responses.view_models;

public class ProductViewModel {

    private String image;
    private String productName;
    private Double price;

    public ProductViewModel() {
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
