package com.app.homeycam.Activities;

import android.os.Handler;
import android.os.Looper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.app.homeycam.Adapters.GuestAdapter;
import com.app.homeycam.CustomDailogs.NewNameDialog;
import com.app.homeycam.CustomeViews.SimpleDividerItemDecoration;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.AddGuestApi.AddGuest;
import com.app.homeycam.ModelClass.GuestUserApi.GuestUsers;
import com.app.homeycam.ModelClass.GuestUserApi.User;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.AddGuestUser.AddGuestUserData;
import com.app.homeycam.Rawheaders.AddGuestUser.Members;
import com.app.homeycam.Rawheaders.Guest.GuestData;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestActivity extends LocalizationActivity {

    RecyclerView guest_users;

    GuestAdapter guestAdapter;

    FloatingActionButton add_user;

    NewNameDialog newNameDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        initView();

    }

    private void initView() {

        guest_users = findViewById(R.id.guest_view);


        add_user = findViewById(R.id.add_user);

        guest_users.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GuestActivity.this);

        guest_users.setLayoutManager(new LinearLayoutManager(GuestActivity.this));

        guest_users.addItemDecoration(new SimpleDividerItemDecoration(GuestActivity.this));

        getGuestUsers();

        onClickevents();
    }

    private void onClickevents() {

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddEmailDailog();
            }
        });
    }


    private void getGuestUsers() {
        try {
            show_loader("Loading");

            GuestData guestData = new GuestData();
            guestData.setProduct_id(loginPrefManager.getStringValue("product_id"));

            apiService.getGuestUsers(loginPrefManager.getUserToken(), guestData).enqueue(new Callback<GuestUsers>() {
                @Override
                public void onResponse(Call<GuestUsers> call, Response<GuestUsers> response) {

                    dismiss_loader();

                    if (response.raw().code() == 200) {
                        if (response.body().getStatus().equalsIgnoreCase("ok")) {
                            List<User> users = response.body().getData().getUsers();


                            guestAdapter = new GuestAdapter(GuestActivity.this, users, new GuestAdapter.ListPlayerAdapterListener() {
                                @Override
                                public void onClickListener(int pos, String type) {

                                    deleteGuest(type);
                                }
                            });


                            guest_users.setAdapter(guestAdapter);


                        }
                    }
                }

                @Override
                public void onFailure(Call<GuestUsers> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }

    private void showAddEmailDailog() {
        newNameDialog = new NewNameDialog(GuestActivity.this, R.style.AppTheme, 2, new NewNameDialog.CancelInterface() {
            @Override
            public void buttonselected(boolean status, String msg) {

                newNameDialog.dismiss();
                if (status) {
                    AddGuestUserData addGuestUserData = new AddGuestUserData();

                    addGuestUserData.setProduct_id(loginPrefManager.getStringValue("product_id"));

                    Members members = new Members();

                    members.setEmail_id(msg);

                    List<Members> members1 = new ArrayList<>();

                    members1.add(members);

                    addGuestUserData.setMembers(members1);

                    Log.e("guestdata", new Gson().toJson(addGuestUserData));

                    addnewGuest(addGuestUserData);
                }
            }
        });

        newNameDialog.show();
    }


    private void addnewGuest(AddGuestUserData addGuestUserData) {
        try {
            show_loader("Loading");

            apiService.addNewGuest(loginPrefManager.getUserToken(), addGuestUserData).enqueue(new Callback<AddGuest>() {
                @Override
                public void onResponse(Call<AddGuest> call, Response<AddGuest> response) {
                    dismiss_loader();

                    if (response.raw().code() == 200 && response.body().getStatus().equalsIgnoreCase("ok")) {
                        showShortMessage(response.body().getMessage());
                        getGuestUsers();
                    } else {
                        showShortMessage(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AddGuest> call, Throwable t) {
                    dismiss_loader();
                }
            });

        } catch (Exception e) {

            dismiss_loader();

        }
    }


    private void deleteGuest(String user_id) {

        JSONObject deleteObject = new JSONObject();
        try {
            deleteObject.put("product_id", loginPrefManager.getStringValue("product_id"));
            deleteObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        local_socket.emit("deleteProduct", deleteObject);

        onDeleteUser();
    }

    private void onDeleteUser() {
        local_socket.on("deleteProduct", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {

                        getGuestUsers();
                    }
                });

            }
        });
    }
}
