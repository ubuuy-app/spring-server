package com.ubuuy.springserver.models.responses.api;

public class AddProductAndPurchaseResponse {
    private Long productId;
    private Long purchaseId;

    public AddProductAndPurchaseResponse() {
    }


    public Long getProductId() {
        return productId;
    }

    public AddProductAndPurchaseResponse setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public AddProductAndPurchaseResponse setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }
}
