package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.enums.ProductPackage;
import com.ubuuy.springserver.models.meta_data.MetaData;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Access(AccessType.PROPERTY)
public class ProductEntity  extends BaseEntity{

    private MetaData metaData;
    private String image;
    private String productName;
    private Double price;
    private ProductPackage productPackage;


    public ProductEntity() {
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MetaData getMetaData() {
        return metaData;
    }

    public ProductEntity setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
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

    @Enumerated(EnumType.STRING)
    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public ProductEntity setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
        return this;
    }
}
