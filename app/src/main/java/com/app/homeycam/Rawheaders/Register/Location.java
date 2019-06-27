package com.app.homeycam.Rawheaders.Register;

import com.google.gson.annotations.SerializedName;

public class Location {


    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


}
