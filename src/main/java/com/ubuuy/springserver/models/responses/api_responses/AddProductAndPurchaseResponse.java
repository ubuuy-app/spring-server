package com.ubuuy.springserver.models.responses.api_responses;

public class AddProductAndPurchaseResponse {

    private Long organizationId;
    private Long productId;
    private Long purchaseId;

    public AddProductAndPurchaseResponse() {
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public AddProductAndPurchaseResponse setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
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
