package com.ubuuy.springserver.models.entities;

import com.ubuuy.springserver.models.enums.ProductPackage;

import javax.persistence.*;


@Entity
@Table(name = "purchases")
@Access(AccessType.PROPERTY)
public class PurchaseEntity extends BaseEntity{

    private ProductEntity product;
    private Integer quantity;
    private ProductPackage productPackage;
    private String priority;
    private StoreEntity store;
    private Boolean exactBrand;
    private MetaEntity metaEntity;

    public PurchaseEntity() {
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

    @Enumerated(EnumType.STRING)
    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public PurchaseEntity setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
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

    @OneToOne(cascade = CascadeType.ALL)
    public MetaEntity getMetaEntity() {
        return metaEntity;
    }

    public PurchaseEntity setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        return this;
    }
}
