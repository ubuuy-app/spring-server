package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.BaseEntity;
import com.ubuuy.springserver.models.entities.MetaEntity;


public class ProductServiceModel extends BaseEntity {

    private String image;
    private String productName;
    private Double price;
    private MetaEntity metaEntity;

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

    public Double getPrice() {
        return price;
    }

    public ProductServiceModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    public MetaEntity getMetaEntity() {
        return metaEntity;
    }

    public ProductServiceModel setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        return this;
    }
}
