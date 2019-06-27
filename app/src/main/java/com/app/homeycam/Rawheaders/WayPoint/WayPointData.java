package com.app.homeycam.Rawheaders.WayPoint;

import com.google.gson.annotations.SerializedName;

public class WayPointData {


    @SerializedName("type_of_booking")
    String type_of_booking;

    @SerializedName("source_id")
    String source_id;

    @SerializedName("destination_id")
    String destination_id;

    @SerializedName("type_of_boat")
    String type_of_boat;

    @SerializedName("time_in_hour")
    String time_in_hour;

    public String getType_of_booking() {
        return type_of_booking;
    }

    public void setType_of_booking(String type_of_booking) {
        this.type_of_booking = type_of_booking;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getType_of_boat() {
        return type_of_boat;
    }

    public void setType_of_boat(String type_of_boat) {
        this.type_of_boat = type_of_boat;
    }

    public String getTime_in_hour() {
        return time_in_hour;
    }

    public void setTime_in_hour(String time_in_hour) {
        this.time_in_hour = time_in_hour;
    }


}
