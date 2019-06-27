package com.app.homeycam.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.app.homeycam.ProgressBar.Loader;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.app.homeycam.ServiceApi.ApiService;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import java.net.URISyntaxException;


public class BaseFragment extends Fragment {

    public LoginPrefManager loginPrefManager;
    public ProgressDialog progressDialog;

    public Socket local_socket;
    public ApiService apiService;
    public Loader loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginPrefManager = new LoginPrefManager(getContext());
        progressDialog = new ProgressDialog(getContext());

        loader = new Loader(getContext());

        apiService = APIServiceFactory.getRetrofit().create(ApiService.class);


        if (local_socket == null) {
            connectSocket(0);
        }


    }


    /*Global Short toast message method*/

    public void showShortToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Socket connectSocket(int i) {


        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        opts.query = "token=" + loginPrefManager.getUserToken() + "&type_of_token=" + "user";
        Log.e("opts", opts.query);
        try {
            local_socket = IO.socket(APIServiceFactory.BASE_URL, opts);

            local_socket.connect();

            local_socket.on("auth", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("auth_data:", new Gson().toJson(args));
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return local_socket;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("onDestory", "---");
    }

    public void show_loader(String message) {
        try {
            if (!getActivity().isFinishing()) {
                if (loader != null && !loader.isShowing()) {
                    loader.show();
                    loader.setMessage(message);
                }
            }
        } catch (Exception e) {

            Log.e("exception", e.getMessage());
            e.printStackTrace();
        }
    }

    public void dismiss_loader() {
        try {
            if (!getActivity().isFinishing()) {
                if (loader != null) {
                    loader.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
