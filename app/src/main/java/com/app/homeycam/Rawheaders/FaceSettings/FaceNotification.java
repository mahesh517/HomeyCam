package com.app.homeycam.Rawheaders.FaceSettings;

import com.google.gson.annotations.SerializedName;

public class FaceNotification {

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getNotification_start() {
        return notification_start;
    }

    public void setNotification_start(String notification_start) {
        this.notification_start = notification_start;
    }

    public String getNotification_end() {
        return notification_end;
    }

    public void setNotification_end(String notification_end) {
        this.notification_end = notification_end;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    @SerializedName("homeimage_id")
    private String image_id;

    @SerializedName("notification_start")
    private String notification_start;

    @SerializedName("notification_end")
    private String notification_end;

    @SerializedName("notification_status")
    private String notification_status;

}
