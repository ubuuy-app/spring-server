package com.ubuuy.springserver.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Access(AccessType.PROPERTY)
public class ProductEntity  extends BaseEntity{

    private String image;
    private String productName;
    private Double price;
    private MetaEntity metaEntity;


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
    public MetaEntity getMetaEntity() {
        return metaEntity;
    }

    public ProductEntity setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        return this;
    }
}
