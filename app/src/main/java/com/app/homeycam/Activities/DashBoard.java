package com.app.homeycam.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.Toast;

import com.app.homeycam.CustomeViews.CustomBottomView.BottomBarHolderActivity;
import com.app.homeycam.CustomeViews.CustomBottomView.NavigationPage;
import com.app.homeycam.Fragments.EventsFragment;
import com.app.homeycam.Fragments.LiveFragment;
import com.app.homeycam.Fragments.SettingsFragment;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends BottomBarHolderActivity implements EventsFragment.OnFragmentInteractionListener, LiveFragment.OnFragmentInteractionListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("token", "" + loginPrefManager.getUserToken());
        Log.e("productid", loginPrefManager.getStringValue("product_id"));

        NavigationPage page1 = new NavigationPage("Events", ContextCompat.getDrawable(this, R.drawable.ic_calendar), EventsFragment.newInstance());
        NavigationPage page2 = new NavigationPage("Live", ContextCompat.getDrawable(this, R.drawable.events), LiveFragment.newInstance());
        NavigationPage page3 = new NavigationPage("Settings", ContextCompat.getDrawable(this, R.drawable.ic_settings), SettingsFragment.newInstance());

        List<NavigationPage> navigationPages = new ArrayList<>();
        navigationPages.add(page1);
        navigationPages.add(page2);
        navigationPages.add(page3);
//        navigationPages.add(page4);

        super.setupBottomBarHolderActivity(navigationPages);


        Log.e("current", loginPrefManager.getUserId());

        getProductInfo();

    }

    private void getProductInfo() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("fetchProductInfo", jsonObject);

        productDetails();
    }

    private void productDetails() {

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


                            loginPrefManager.setStringValue("recording_start", recording_start);
                            loginPrefManager.setStringValue("recording_end", recording_end);
                            loginPrefManager.setStringValue("recording_status", status);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }


    @Override
    public void onClicked() {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    }
}
