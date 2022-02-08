package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.BaseEntity;
import com.ubuuy.springserver.models.entities.StoreEntity;

import java.util.List;


public class ProductServiceModel extends BaseEntity {

    private String image;
    private String productName;
    private String priority;
    private Boolean exactBrand;
    private List<StoreEntity> storeEntity;

    public ProductServiceModel() {
    }

    public String getImage() {
        return image;
    }

    public ProductServiceModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductServiceModel setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public ProductServiceModel setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public ProductServiceModel setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    public List<StoreEntity> getStoreEntity() {
        return storeEntity;
    }

    public ProductServiceModel setStoreEntity(List<StoreEntity> storeEntity) {
        this.storeEntity = storeEntity;
        return this;
    }
}
