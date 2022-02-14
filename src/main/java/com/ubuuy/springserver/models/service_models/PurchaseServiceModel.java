package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.ProductEntity;
import com.ubuuy.springserver.models.entities.StoreEntity;
import com.ubuuy.springserver.models.enums.ProductPackage;
import com.ubuuy.springserver.models.meta_data.MetaData;

public class PurchaseServiceModel extends BaseServiceModel {

    private MetaData metaData;
    private ProductEntity product;
    private Integer quantity;
    private ProductPackage productPackage;
    private String priority;
    private StoreEntity store;
    private Boolean exactBrand;
    private Boolean isBought;


    public PurchaseServiceModel() {
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public PurchaseServiceModel setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public PurchaseServiceModel setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseServiceModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public PurchaseServiceModel setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public PurchaseServiceModel setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public StoreEntity getStore() {
        return store;
    }

    public PurchaseServiceModel setStore(StoreEntity store) {
        this.store = store;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public PurchaseServiceModel setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    public Boolean getBought() {
        return isBought;
    }

    public PurchaseServiceModel setBought(Boolean bought) {
        isBought = bought;
        return this;
    }
}
