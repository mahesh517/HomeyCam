package com.app.homeycam.FCM;


import android.util.Log;

import com.app.homeycam.AppController.HomeyCam;
import com.app.homeycam.Utils.LoginPrefManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private LoginPrefManager Preference;


    @Override
    @android.support.annotation.WorkerThread
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            Log.e("Refreshed token: ", "" + refreshedToken);
            Preference = new LoginPrefManager(HomeyCam.getContext());
            Preference.setStringValue("device_token", "" + refreshedToken);
            Preference.setDeviceToken(refreshedToken);
        }

    }


}