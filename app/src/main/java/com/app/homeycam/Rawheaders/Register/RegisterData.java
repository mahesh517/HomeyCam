package com.app.homeycam.Rawheaders.Register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterData implements Serializable {


    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("mobileno")
    private String mobileno;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private String gender;

    @SerializedName("country_code")
    private String country_code;

    @SerializedName("dob")
    private String dob;

    @SerializedName("emergency_contact")
    private String emergency_contact;

    @SerializedName("user_photo")
    private String user_photo;

    @SerializedName("role")
    private String role;

    @SerializedName("device_id")
    private String device_id;

    @SerializedName("platform")
    private String platform;

    @SerializedName("location")
    private Location location;

    @SerializedName("businessID")
    private String businessID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }
}
