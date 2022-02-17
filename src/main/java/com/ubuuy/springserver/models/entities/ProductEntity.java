package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.meta_data.MetaData;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Access(AccessType.PROPERTY)
public class ProductEntity  extends BaseEntity{

    private String image;
    private String productName;
    private Double price;
    private MetaData metaData;


    public ProductEntity() {
    }

    public String getImage() {
        return image;
    }

    public ProductEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductEntity setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductEntity setPrice(Double price) {
        this.price = price;
        return this;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MetaData getMetaData() {
        return metaData;
    }

    public ProductEntity setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }
}
