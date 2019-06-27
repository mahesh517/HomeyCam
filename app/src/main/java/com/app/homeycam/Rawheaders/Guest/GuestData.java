package com.app.homeycam.Rawheaders.Guest;

import com.google.gson.annotations.SerializedName;

public class GuestData {

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @SerializedName("product_id")
    private String product_id;
}
