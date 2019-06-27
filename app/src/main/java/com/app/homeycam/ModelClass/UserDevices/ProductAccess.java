
package com.app.homeycam.ModelClass.UserDevices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductAccess {

    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product_id")
    @Expose
    private ProductId productId;

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

}
