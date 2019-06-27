package com.app.homeycam.Rawheaders.PassengerTrip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengerTripData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("date")
    @Expose
    private String date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}