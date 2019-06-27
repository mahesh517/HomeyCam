
package com.app.homeycam.ModelClass.Login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("dropbox")
    @Expose
    private Dropbox dropbox;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("connected_sockets")
    @Expose
    private List<String> connectedSockets = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("invalidate_token")
    @Expose
    private String invalidateToken;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("product_access")
    @Expose
    private List<ProductAccess> productAccess = null;
    @SerializedName("sockets")
    @Expose
    private List<Object> sockets = null;
    @SerializedName("stream_status")
    @Expose
    private String streamStatus;
    @SerializedName("platform")
    @Expose
    private List<Platform> platform = null;

    public Dropbox getDropbox() {
        return dropbox;
    }

    public void setDropbox(Dropbox dropbox) {
        this.dropbox = dropbox;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getConnectedSockets() {
        return connectedSockets;
    }

    public void setConnectedSockets(List<String> connectedSockets) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ProductAccess> getProductAccess() {
        return productAccess;
    }

    public void setProductAccess(List<ProductAccess> productAccess) {
        this.productAccess = productAccess;
    }

    public List<Object> getSockets() {
        return sockets;
    }

    public void setSockets(List<Object> sockets) {
        this.sockets = sockets;
    }

    public String getStreamStatus() {
        return streamStatus;
    }

    public void setStreamStatus(String streamStatus) {
        this.streamStatus = streamStatus;
    }

    public List<Platform> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Platform> platform) {
        this.platform = platform;
    }

}
