package com.app.homeycam.Rawheaders.ForgotPassword;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordData {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("old_password")
    private String old_password;

    @SerializedName("new_password")
    private String new_password;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }


}
