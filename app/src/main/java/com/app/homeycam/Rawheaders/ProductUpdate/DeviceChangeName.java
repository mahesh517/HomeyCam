package com.app.homeycam.Rawheaders.ProductUpdate;

import com.google.gson.annotations.SerializedName;

public class DeviceChangeName {

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @SerializedName("product_name")
    private String product_name;
}
