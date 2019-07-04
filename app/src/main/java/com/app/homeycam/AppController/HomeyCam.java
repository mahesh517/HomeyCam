package com.app.homeycam.AppController;

import android.content.Context;
import android.os.StrictMode;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.util.Log;

import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.firebase.FirebaseApp;

import java.net.URISyntaxException;

public class HomeyCam extends MultiDexApplication {

    private static Context applicationContext = null;

    public Socket local_socket;

    LoginPrefManager loginPrefManager;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);


        applicationContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        loginPrefManager = new LoginPrefManager(this);


        connectSocket();
        MultiDex.install(this);
    }

    public static Context getContext() {
        return applicationContext;
    }

    private Socket connectSocket() {


        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        opts.query = "token=" + loginPrefManager.getUserToken() + "&type_of_token=" + "user";
        Log.e("opts", opts.query);
        try {
            local_socket = IO.socket(APIServiceFactory.BASE_URL, opts);

            local_socket.connect();



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return local_socket;
    }


    public Socket getSocket() {
        return local_socket;
    }


}