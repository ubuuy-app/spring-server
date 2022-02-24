package com.ubuuy.springserver.models.requests;

import com.ubuuy.springserver.models.enums.ProductPackage;

public class AddPurchaseAndProductRequest {

    private String image;
    private String productName;
    private String priority;
    private Boolean exactBrand;
    private ProductPackage productPackage;
    private Integer quantity;
    private String store;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public void setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public AddPurchaseAndProductRequest setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public AddPurchaseAndProductRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getStore() {
        return store;
    }

    public AddPurchaseAndProductRequest setStore(String store) {
        this.store = store;
        return this;
    }
}
