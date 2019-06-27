package com.app.homeycam.Rawheaders.AddGuestUser;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddGuestUserData {

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @SerializedName("product_id")
    String product_id;

    public List<Members> getMembers() {
        return members;
    }

    public void setMembers(List<Members> members) {
        this.members = members;
    }

    @SerializedName("members")
    List<Members> members;
}
