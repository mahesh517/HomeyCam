package com.app.homeycam.Rawheaders.Rate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateData {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("rated_to")
    @Expose
    private String ratedTo;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRatedTo() {
        return ratedTo;
    }

    public void setRatedTo(String ratedTo) {
        this.ratedTo = ratedTo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}