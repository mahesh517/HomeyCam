package com.app.homeycam.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.app.homeycam.Adapters.DeviceAdapter;
import com.app.homeycam.CustomeViews.CustomBottomView.BottomBarHolderActivity;
import com.app.homeycam.CustomeViews.SimpleDividerItemDecoration;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.UserDevices.ProductAccess;
import com.app.homeycam.ModelClass.UserDevices.User;
import com.app.homeycam.ModelClass.UserDevices.UserDevices;
import com.app.homeycam.R;

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

                            deviceAdapter = new DeviceAdapter(DevicesActivity.this, products, new DeviceAdapter.ListPlayerAdapterListener() {
                                @Override
                                public void onClickListener(int pos, String user_id) {

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

}
