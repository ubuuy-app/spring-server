package com.ubuuy.springserver.models.requests;

public class AddProductRequest {

    private String image;
    private String productName;
    private String priority;
    private Boolean exactBrand;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getExactBrand() {
        return exactBrand;
    }

    public void setExactBrand(Boolean exactBrand) {
        this.exactBrand = exactBrand;
    }
}
