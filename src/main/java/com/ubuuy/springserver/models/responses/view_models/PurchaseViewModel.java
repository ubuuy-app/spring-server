package com.ubuuy.springserver.models.responses.view_models;

import com.ubuuy.springserver.models.entities.StoreEntity;
import com.ubuuy.springserver.models.enums.ProductPackage;

public class PurchaseViewModel {

    private ProductViewModel product;
    private Integer quantity;
    private ProductPackage productPackage;
    private String priority;
    private StoreEntity store;
    private Boolean exactBrand;
    private Boolean isBought;

    public PurchaseViewModel() {
    }

    public ProductViewModel getProduct() {
        return product;
    }

    public PurchaseViewModel setProduct(ProductViewModel product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public PurchaseViewModel setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public PurchaseViewModel setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public StoreEntity getStore() {
        return store;
    }

    public PurchaseViewModel setStore(StoreEntity store) {
        this.store = store;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public PurchaseViewModel setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    public Boolean getBought() {
        return isBought;
    }

    public PurchaseViewModel setBought(Boolean bought) {
        isBought = bought;
        return this;
    }
}
