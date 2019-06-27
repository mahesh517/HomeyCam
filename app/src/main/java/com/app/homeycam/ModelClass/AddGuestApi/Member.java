
package com.app.homeycam.ModelClass.AddGuestApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Member {

    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("exist")
    @Expose
    private Boolean exist;
    @SerializedName("status")
    @Expose
    private String status;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
