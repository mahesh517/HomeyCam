package com.app.homeycam.Rawheaders.AddGuestUser;

import com.google.gson.annotations.SerializedName;

public class Members {

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    @SerializedName("email_id")
    private String email_id;
}
