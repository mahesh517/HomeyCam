package com.app.homeycam.Rawheaders.ForgotPassword;

import com.google.gson.annotations.SerializedName;

public class ForgotPasswordData {

    @SerializedName("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
