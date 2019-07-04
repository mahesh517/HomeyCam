package com.app.homeycam.ModelClass.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceSettings {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("success")
    @Expose
    private boolean success;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    @Expose
    private String message;
}
