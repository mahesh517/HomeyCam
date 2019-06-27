
package com.app.homeycam.ModelClass.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dropbox {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("token_valid")
    @Expose
    private Boolean tokenValid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getTokenValid() {
        return tokenValid;
    }

    public void setTokenValid(Boolean tokenValid) {
        this.tokenValid = tokenValid;
    }

}
