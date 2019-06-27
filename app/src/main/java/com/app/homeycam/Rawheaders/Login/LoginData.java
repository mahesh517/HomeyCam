package com.app.homeycam.Rawheaders.Login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("device_id")
    private String device_id;

    @SerializedName("platform")
    private String platform;

    @SerializedName("role")
    private String role;


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
