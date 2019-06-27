package com.app.homeycam.Rawheaders.SocketHeaders;

import com.google.gson.annotations.SerializedName;

public class CaptainStatus {


    @SerializedName("user_id")
    String user_id;

    @SerializedName("status")
    String status;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
