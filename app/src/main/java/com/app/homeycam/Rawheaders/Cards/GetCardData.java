package com.app.homeycam.Rawheaders.Cards;

import com.google.gson.annotations.SerializedName;

public class GetCardData {

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @SerializedName("user_id")
    private String user_id;
}
