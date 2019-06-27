
package com.app.homeycam.ModelClass.UserDevices;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product_access")
    @Expose
    private List<ProductAccess> productAccess = null;
    @SerializedName("online_stream_product_id")
    @Expose
    private OnlineStreamProductId onlineStreamProductId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductAccess> getProductAccess() {
        return productAccess;
    }

    public void setProductAccess(List<ProductAccess> productAccess) {
        this.productAccess = productAccess;
    }

    public OnlineStreamProductId getOnlineStreamProductId() {
        return onlineStreamProductId;
    }

    public void setOnlineStreamProductId(OnlineStreamProductId onlineStreamProductId) {
        this.onlineStreamProductId = onlineStreamProductId;
    }

}
