
package com.app.homeycam.ModelClass.GuestUserApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Platform {

    @SerializedName("platform_device_id")
    @Expose
    private List<String> platformDeviceId = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("platform_name")
    @Expose
    private String platformName;

    public List<String> getPlatformDeviceId() {
        return platformDeviceId;
    }

    public void setPlatformDeviceId(List<String> platformDeviceId) {
        this.platformDeviceId = platformDeviceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

}
