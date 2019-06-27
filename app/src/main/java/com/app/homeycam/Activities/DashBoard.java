package com.app.homeycam.Activities;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.homeycam.CustomeViews.CustomBottomView.BottomBarHolderActivity;
import com.app.homeycam.CustomeViews.CustomBottomView.NavigationPage;
import com.app.homeycam.CustomeViews.RangeTimePickerDialog;
import com.app.homeycam.Fragments.EventsFragment;
import com.app.homeycam.Fragments.LiveFragment;
import com.app.homeycam.Fragments.SettingsFragment;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
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


        long time = 567648000 - System.currentTimeMillis();
        Log.e("current", String.valueOf(time));

        checkFaces();
    }

    private void checkFaces() {

        local_socket.on("fetchall", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];

                Log.e("jsonObject", jsonObject.toString());
            }
        });
    }


    @Override
    public void onClicked() {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
    }
}
