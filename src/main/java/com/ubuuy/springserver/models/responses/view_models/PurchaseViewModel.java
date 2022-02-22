package com.ubuuy.springserver.models.responses.view_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ubuuy.springserver.models.entities.StoreEntity;
import com.ubuuy.springserver.models.meta_data.MetaData;

public class PurchaseViewModel {

    private Long id;
    private MetaData metaData;
    private ProductViewModel product;
    private Integer quantity;
    private String priority;
    private StoreEntity store;
    private Boolean exactBrand;
    private Boolean isBought;

    public PurchaseViewModel() {
    }

    @JsonProperty("_id")
    public Long getId() {
        return id;
    }

    public PurchaseViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public PurchaseViewModel setMetaData(MetaData metaData) {
        this.metaData = metaData;
        return this;
    }

    public ProductViewModel getProduct() {
        return product;
    }

    public PurchaseViewModel setProduct(ProductViewModel product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public PurchaseViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

     public String getPriority() {
         return priority;
     }

    public PurchaseViewModel setPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public StoreEntity getStore() {
        return store;
    }

    public PurchaseViewModel setStore(StoreEntity store) {
        this.store = store;
        return this;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public PurchaseViewModel setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
        return this;
    }

    public Boolean getBought() {
        return isBought;
    }

    public PurchaseViewModel setBought(Boolean bought) {
        isBought = bought;
        return this;
    }
}
