
package com.app.homeycam.ModelClass.GuestUserApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("connected_sockets")
    @Expose
    private List<Object> connectedSockets = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("invalidate_token")
    @Expose
    private String invalidateToken;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("product_access")
    @Expose
    private List<ProductAccess> productAccess = null;
    @SerializedName("platform")
    @Expose
    private List<Platform> platform = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("stream_status")
    @Expose
    private String streamStatus;
    @SerializedName("online_stream_product_id")
    @Expose
    private String onlineStreamProductId;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Object> getConnectedSockets() {
        return connectedSockets;
    }

    public void setConnectedSockets(List<Object> connectedSockets) {
        this.connectedSockets = connectedSockets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvalidateToken() {
        return invalidateToken;
    }

    public void setInvalidateToken(String invalidateToken) {
        this.invalidateToken = invalidateToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductAccess> getProductAccess() {
        return productAccess;
    }

    public void setProductAccess(List<ProductAccess> productAccess) {
        this.productAccess = productAccess;
    }

    public List<Platform> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Platform> platform) {
        this.platform = platform;
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

    public String getStreamStatus() {
        return streamStatus;
    }

    public void setStreamStatus(String streamStatus) {
        this.streamStatus = streamStatus;
    }

    public String getOnlineStreamProductId() {
        return onlineStreamProductId;
    }

    public void setOnlineStreamProductId(String onlineStreamProductId) {
        this.onlineStreamProductId = onlineStreamProductId;
    }

}
