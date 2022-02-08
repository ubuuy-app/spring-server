package com.ubuuy.springserver.models.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Access(AccessType.PROPERTY)
public class ProductEntity  extends BaseEntity{

    private String image;
    private String productName;
    private String priority;
    private Boolean exactBrand;
    private List<StoreEntity> stores;

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

    public String getPriority() {
        return priority;
    }

    public ProductEntity setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public ProductEntity setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    @ManyToMany
    public List<StoreEntity> getStores() {
        return stores;
    }

    public ProductEntity setStores(List<StoreEntity> stores) {
        this.stores = stores;
        return this;
    }
}
