package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.meta_data.MetaData;

import javax.persistence.*;


@Entity
@Table(name = "purchases")
@Access(AccessType.PROPERTY)
public class PurchaseEntity extends BaseEntity{

    private MetaData metaData;
    private ProductEntity product;
    private Integer quantity;
    private String priority;
    private StoreEntity store;
    private Boolean exactBrand;
    private Boolean isBought;

    public PurchaseEntity() {
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MetaData getMetaData() {
        return metaData;
    }

    public PurchaseEntity setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public ProductEntity getProduct() {
        return product;
    }

    public PurchaseEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public PurchaseEntity setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    @OneToOne
    public StoreEntity getStore() {
        return store;
    }

    public PurchaseEntity setStore(StoreEntity store) {
        this.store = store;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public PurchaseEntity setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    @Column(name = "is_bought")
    public Boolean getBought() {
        return isBought;
    }

    public PurchaseEntity setBought(Boolean bought) {
        isBought = bought;
        return this;
    }
}
