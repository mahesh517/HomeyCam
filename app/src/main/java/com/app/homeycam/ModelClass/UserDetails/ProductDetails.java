
package com.app.homeycam.ModelClass.UserDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetails {

    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("allevent_lastfetch")
    @Expose
    private String alleventLastfetch;
    @SerializedName("last_seen")
    @Expose
    private String lastSeen;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getAlleventLastfetch() {
        return alleventLastfetch;
    }

    public void setAlleventLastfetch(String alleventLastfetch) {
        this.alleventLastfetch = alleventLastfetch;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

}
