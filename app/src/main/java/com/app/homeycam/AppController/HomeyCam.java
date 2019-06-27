package com.app.homeycam.AppController;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class HomeyCam extends MultiDexApplication {

    private static Context applicationContext = null;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);


        applicationContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        MultiDex.install(this);
    }

    public static Context getContext() {
        return applicationContext;
    }


}