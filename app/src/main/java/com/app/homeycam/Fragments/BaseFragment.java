package com.app.homeycam.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.app.homeycam.ProgressBar.Loader;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.app.homeycam.ServiceApi.ApiService;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.socketio.client.Socket;


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


    }


    /*Global Short toast message method*/

    public void showShortToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
