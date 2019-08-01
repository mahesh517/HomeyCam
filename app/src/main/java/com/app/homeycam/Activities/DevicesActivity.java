package com.app.homeycam.Activities;

import com.app.homeycam.CustomDailogs.ChangeDeviceNameDialog;
import com.app.homeycam.ModelClass.DeviceUpdate.DeviceNameUpdate;
import com.app.homeycam.Rawheaders.ProductUpdate.DeviceChangeName;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.homeycam.Adapters.DeviceAdapter;
import com.app.homeycam.CustomeViews.SimpleDividerItemDecoration;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.UserDevices.ProductAccess;
import com.app.homeycam.ModelClass.UserDevices.User;
import com.app.homeycam.ModelClass.UserDevices.UserDevices;
import com.app.homeycam.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevicesActivity extends LocalizationActivity {

    List<ProductAccess> productAccesses;
    List<String> products;
    RecyclerView devices;

    DeviceAdapter deviceAdapter;
    FloatingActionButton add_user;

    String product_id;

    DeviceChangeName deviceChangeName;

    ChangeDeviceNameDialog changeDeviceNameDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        initiview();
    }

    private void initiview() {


        add_user = findViewById(R.id.add_user);
        devices = findViewById(R.id.device_view);

        devices.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DevicesActivity.this);

        devices.setLayoutManager(new LinearLayoutManager(DevicesActivity.this));

        devices.addItemDecoration(new SimpleDividerItemDecoration(DevicesActivity.this));

        getProducts();
    }

    private void getProducts() {
        try {

            show_loader("Loading");

            apiService.getConnectedDevices(loginPrefManager.getUserToken(), loginPrefManager.getUserId()).enqueue(new Callback<UserDevices>() {
                @Override
                public void onResponse(Call<UserDevices> call, Response<UserDevices> response) {

                    dismiss_loader();
                    products = new ArrayList<>();

                    if (response.body().getStatus().equalsIgnoreCase("ok")) {

                        User user = response.body().getData().getUser();

                        if (user != null) {

                            productAccesses = user.getProductAccess();

                            if (productAccesses.size() > 0) {

                                for (int i = 0; i < productAccesses.size(); i++) {
                                    products.add(productAccesses.get(i).getProductId().getProductName());
                                }
                            }

                            deviceAdapter = new DeviceAdapter(DevicesActivity.this, products, new DeviceAdapter.DeviceAdapterInterface() {
                                @Override
                                public void onClickListener(int pos, int user_id) {

                                    if (pos == 1) {
                                        product_id = productAccesses.get(user_id).getProductId().getId();
                                        showDialog();
                                    } else if (pos == 2) {
                                        loginPrefManager.setWifistatus("change");
                                        goToActivity(DevicesActivity.this, HomeActivity.class, null);
                                    } else if (pos == 3) {
                                        loginPrefManager.setWifistatus("reset");
                                        goToActivity(DevicesActivity.this, HomeActivity.class, null);
                                    }
                                }
                            });

                            devices.setAdapter(deviceAdapter);

                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDevices> call, Throwable t) {

                }
            });


        } catch (Exception e) {

        }
    }

    private void showDialog() {


        changeDeviceNameDialog = new ChangeDeviceNameDialog(DevicesActivity.this, R.style.AppTheme, new ChangeDeviceNameDialog.DeviceNameInterface() {
            @Override
            public void onName(String name) {

                deviceChangeName = new DeviceChangeName();

                deviceChangeName.setProduct_name(name);
                updateDeviceName();


            }
        });

        changeDeviceNameDialog.show();
    }

    private void updateDeviceName() {

        try {

            show_loader("");
            apiService.updateDeviceName(loginPrefManager.getUserToken(), deviceChangeName, product_id).enqueue(new Callback<DeviceNameUpdate>() {
                @Override
                public void onResponse(Call<DeviceNameUpdate> call, Response<DeviceNameUpdate> response) {

                    dismiss_loader();

                    loginPrefManager.setDeviceUpdated(true);
                    getProducts();
                }

                @Override
                public void onFailure(Call<DeviceNameUpdate> call, Throwable t) {

                }
            });

        } catch (Exception e) {

        }
    }

    private void deleteDevice() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
            jsonObject.put("user_id", loginPrefManager.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("deleteProduct", jsonObject);
    }

}
