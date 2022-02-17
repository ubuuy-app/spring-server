package com.ubuuy.springserver.models.service_models;

import com.ubuuy.springserver.models.entities.BaseEntity;
import com.ubuuy.springserver.models.meta_data.MetaData;


public class ProductServiceModel extends BaseEntity {

    private String image;
    private String productName;
    private Double price;
    private MetaData metaData;

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

    public MetaData getMetaData() {
        return metaData;
    }

    public ProductServiceModel setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }
}
