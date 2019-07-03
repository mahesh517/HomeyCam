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
import com.app.homeycam.Utils.LoginPrefManager;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {


    Handler handler;
    Runnable runnable;

    public LoginPrefManager loginPrefManager;

    @SuppressLint("WrongThread")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loginPrefManager = new LoginPrefManager(SplashScreenActivity.this);
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
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                } else {

                    if (loginPrefManager.getStringValue("product_id").equalsIgnoreCase("")) {

                        startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, DashBoard.class));
                    }

                    finish();
                }


            }
        };


        checkWifiPermissions();


    }

    private void checkWifiPermissions() {

        Dexter.withActivity(SplashScreenActivity.this)
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

                Log.e("report", new Gson().toJson(report));

                if (report.areAllPermissionsGranted()) {
                    handler.postDelayed(runnable, 3000);
                } else {
                    checkWifiPermissions();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {


            }
        }).check();
//
//        PermissionHelper permissionHelper = new PermissionHelper(SplashScreenActivity.this);
//        permissionHelper.check(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE).
//                withDialogBeforeRun(R.string.dialog_before_run_title, R.string.dialog_location_before_run_message, R.string.dialog_positive_button)
//                .setDialogPositiveButtonColor(R.color.colorPrimary)
//                .onSuccess(this::onSuccessProfile)
//                .onDenied(this::onDeniedProfile)
//                .onNeverAskAgain(this::onNeverAskAgainProfile)
//                .run();
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
