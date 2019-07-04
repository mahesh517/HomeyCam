package com.app.homeycam.Rawheaders.Settings;

import com.google.gson.annotations.SerializedName;

public class FaceNotifications {


    @SerializedName("homeimage_id")
    String homeimage_id;

    @SerializedName("notification_start")
    String notification_start;

    @SerializedName("notification_end")
    String notification_end;

    @SerializedName("notification_status")

    String notification_status;

    public String getHomeimage_id() {
        return homeimage_id;
    }

    public void setHomeimage_id(String homeimage_id) {
        this.homeimage_id = homeimage_id;
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


}
