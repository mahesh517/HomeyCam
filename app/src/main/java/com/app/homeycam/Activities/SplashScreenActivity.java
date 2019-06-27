package com.app.homeycam.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.app.homeycam.FCM.MyFirebaseInstanceIDService;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.PermissionChecker.PermissionHelper;
import com.app.homeycam.R;

public class SplashScreenActivity extends LocalizationActivity {


    Handler handler;
    Runnable runnable;

    @SuppressLint("WrongThread")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        loginPrefManager.setDeviceId(android_id);

        if (loginPrefManager.getDeviceToken().equalsIgnoreCase("")) {
            MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
            myFirebaseInstanceIDService.onTokenRefresh();
        }

        Log.e("device_token", loginPrefManager.getDeviceToken());
        runnable = new Runnable() {
            @Override
            public void run() {


                if (loginPrefManager.getUserToken().equalsIgnoreCase("")) {
                    goToActivity(SplashScreenActivity.this, LoginActivity.class, null);
                    finish();
                } else {

                    if (loginPrefManager.getStringValue("product_id").equalsIgnoreCase("")) {
                        goToActivity(SplashScreenActivity.this, HomeActivity.class, null);
                    } else {
                        goToActivity(SplashScreenActivity.this, DashBoard.class, null);
                    }

                    finish();
                }


            }
        };


        checkWifiPermissions();


    }

    private void checkWifiPermissions() {

        permissionHelper.check(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE).
                withDialogBeforeRun(R.string.dialog_before_run_title, R.string.dialog_location_before_run_message, R.string.dialog_positive_button)
                .setDialogPositiveButtonColor(R.color.colorPrimary)
                .onSuccess(this::onSuccessProfile)
                .onDenied(this::onDeniedProfile)
                .onNeverAskAgain(this::onNeverAskAgainProfile)
                .run();
    }

    private void onSuccessProfile() {

        handler.postDelayed(runnable, 3000);

    }

    private void onNeverAskAgainProfile() {
        Log.e("Nvert,", "Ask");
    }

    private void onDeniedProfile() {

        Log.e("onDeined", "onDeined");
//        showNointerntView(getString(R.string.onDenied));
    }


}
