package com.app.homeycam.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.homeycam.Activities.AccountActivity;
import com.app.homeycam.Activities.DevicesActivity;
import com.app.homeycam.Activities.FamilyActivity;
import com.app.homeycam.Activities.GuestActivity;
import com.app.homeycam.Adapters.RecyclerViewAdapter;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.Settings.ItemType;
import com.app.homeycam.Rawheaders.Settings.Model;
import com.github.nkzawa.emitter.Emitter;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends BaseFragment {

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;

    String localip;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);


        getProductInfo(view);


        return view;
    }

    private void initAdapter(View view, String recording_start, String recording_end, String status) {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(getActivity(), local_socket, recording_start, recording_end, status, new RecyclerViewAdapter.SettingInterface() {
            @Override
            public void onClick(String name) {
                Log.e("name", "--" + name);

                selectedItem(name);
            }
        });
        initData();


        getLocalip();
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        recyclerView.setAdapter(adapter);
    }

    private void getLocalip() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        local_socket.emit("getLocalIp", jsonObject);

        listenLocalip();
    }

    private void listenLocalip() {

        local_socket.on("sendUserToLocalIp", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];

                Log.e("jsonObjecy", jsonObject.toString());
                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });
    }

    private void initData() {
        List<Model> modelList = new ArrayList<>();
        for (Integer i = 1; i < 10; i++) {

            modelList.add(setModel(i));
            for (Integer j = 1; j < 2; j++) {
                Model model = null;

                if (i == 1) {
                    model = new Model(setSubitems(i), ItemType.Alerts);
                }
                if (i > 1 && i <= 6) {
                    model = new Model(setSubitems(i), ItemType.Post);
                } else if ((i > 6)) {
                    model = new Model(setSubitems(i), 4);
                }
                modelList.add(model);
            }
        }
        adapter.submitList(modelList);


    }


    private String setSubitems(int position) {

        String headerModel = null;
        switch (position) {
            case 1:
                headerModel = "Alerts and Recording";
                break;
            case 2:
                headerModel = "Family";
                break;
            case 3:
                headerModel = "Dropbox";

                break;
            case 4:
                headerModel = "Guests";
                break;
            case 5:

                headerModel = "Products";
                break;
            case 6:

                headerModel = "Account";
                break;
            case 7:

                headerModel = "Help";
                break;
            case 8:

                headerModel = "Privacy";
                break;

            case 9:

                headerModel = "Terms and conditions";
                break;

            case 10:
                headerModel = "Terms and conditions";
                break;
        }

        return headerModel;

    }

    private Model setModel(int position) {

        Model headerModel = null;

        switch (position) {
            case 1:
                headerModel = new Model("Alerts and Recording", ItemType.Header);
                break;
            case 2:
                headerModel = new Model("Family", ItemType.Header);
                break;
            case 3:
                headerModel = new Model("Dropbox", ItemType.Header);

                break;
            case 4:
                headerModel = new Model("Guests", ItemType.Header);
                break;
            case 5:

                headerModel = new Model("Products", ItemType.Header);
                break;
            case 6:

                headerModel = new Model("Account", ItemType.Header);
                break;
            case 7:

                headerModel = new Model("Help", ItemType.Normal);
                break;
            case 8:

                headerModel = new Model("Privacy", ItemType.Normal);
                break;

            case 9:

                headerModel = new Model("Terms and conditions", ItemType.Normal);
                break;

            case 10:
                headerModel = new Model("Terms and conditions", ItemType.Normal);
                break;
        }

        return headerModel;
    }


    private void selectedItem(String name) {

        switch (name) {


            case "Alerts and Recording":
                break;
            case "Family":

                Intent intent = new Intent(getActivity(), FamilyActivity.class);
                startActivity(intent);
                break;
            case "Dropbox":

                break;
            case "Guests":

                Intent intent1 = new Intent(getActivity(), GuestActivity.class);
                startActivity(intent1);
                break;
            case "Products":

                Intent intent21 = new Intent(getActivity(), DevicesActivity.class);
                startActivity(intent21);
                break;
            case "Account":

                Intent intent2 = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent2);
            case "Help":
                break;
            case "Privacy":
                break;
            case "Terms and conditions":
                break;

            case "from_time":
                break;
            case "to_time":
                break;
        }

    }

    private void getProductInfo(View view) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("fetchProductInfo", jsonObject);

        productDetails(view);
    }

    private void productDetails(View view) {

        local_socket.on("fetchProductInfo", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {

                        try {
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONObject result = data.getJSONObject("result");

                            JSONObject settings = result.getJSONObject("settings");

                            Log.e("settings", settings.toString());

                            String recording_start = settings.getString("recording_start");
                            String recording_end = settings.getString("recording_end");
                            String status = settings.getString("recording_status");

                            initAdapter(view, recording_start, recording_end, status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

}
