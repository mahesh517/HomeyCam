package com.app.homeycam.Rawheaders.SocketHeaders;

import com.google.gson.annotations.SerializedName;

public class ConnectData {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType_of_token() {
        return type_of_token;
    }

    public void setType_of_token(String type_of_token) {
        this.type_of_token = type_of_token;
    }

    @SerializedName("type_of_token")
    private String type_of_token;

}
