
package com.app.homeycam.ModelClass.UserDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextmonthData {

    @SerializedName("messageCode")
    @Expose
    private String messageCode;
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
