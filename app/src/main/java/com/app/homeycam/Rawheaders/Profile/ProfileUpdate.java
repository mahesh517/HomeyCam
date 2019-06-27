package com.app.homeycam.Rawheaders.Profile;

import com.google.gson.annotations.SerializedName;

public class ProfileUpdate {


    @SerializedName("name")
    private String name;

    @SerializedName("mobileno")
    private String mobileno;

    @SerializedName("email")
    private String email;

    @SerializedName("emergency_contact")
    private String emergency_contact;

    @SerializedName("user_photo")
    private String user_photo;

    @SerializedName("type_of_boat")
    private String type_of_boat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }


    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public void setType_of_boat(String type_of_boat) {
        this.type_of_boat = type_of_boat;
    }
}
