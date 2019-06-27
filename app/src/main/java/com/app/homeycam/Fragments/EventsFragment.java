package com.app.homeycam.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.VideoView;

import com.app.homeycam.Activities.PlayVidoeActivty;
import com.app.homeycam.Adapters.EventAdapter;
import com.app.homeycam.CustomDailogs.EventDialog;
import com.app.homeycam.R;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import co.ceryle.segmentedbutton.SegmentedButton;
import co.ceryle.segmentedbutton.SegmentedButtonGroup;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Adib on 13-Apr-17.
 */

public class EventsFragment extends BaseFragment {

    private OnFragmentInteractionListener listener;

    private String urlStream;
    private VideoView myVideoView;
    private URL url;

    SegmentedButton user, run, notification, all;

    SegmentedButtonGroup buttonGroup;

    private View view;

    EventAdapter eventAdapter;

    RecyclerView events;

    public LoginPrefManager loginPrefManager;

    int selected_event_type = 0;

    JSONArray total_array, selected = null;

    JSONObject selected_object;

    EventDialog eventDialog;

    String event_video_url, event_name;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loginPrefManager = new LoginPrefManager(getActivity());

        view = inflater.inflate(R.layout.fragment_first, container, false);


        user = view.findViewById(R.id.user_btn);
        run = view.findViewById(R.id.user_run_btn);
        notification = view.findViewById(R.id.noti_btn);
        all = view.findViewById(R.id.all_btn);
        events = view.findViewById(R.id.event_view);
        buttonGroup = view.findViewById(R.id.dynamic_drawable_group);

        events.setLayoutManager(new LinearLayoutManager(getActivity()));
        events.setHasFixedSize(true);

        connectSocket(0);
        onClickevents();

        if (local_socket != null) {
            emit_Events();
        } else {
            Log.e("local_socket", "--" + local_socket.connected());
        }
        return view;
    }

    private void onClickevents() {


        buttonGroup.setOnClickedButtonListener(new SegmentedButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(int position) {
                selected_event_type = position;

                if (total_array == null) {
                    checkevents();
                } else {
                    setData();
                }

            }
        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }


    private void emit_Events() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
            jsonObject.put("event_type", "alltype");
//            jsonObject.put("multipleface", "alltype");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonObject", jsonObject.toString());

        local_socket.emit("alloccurevent", jsonObject);

        checkevents();

    }

    private void checkevents() {

//        Log.e("check", "----");

//        Log.e("local_socket", "--" + local_socket.connected());

        local_socket.on("alloccurevent", new Emitter.Listener() {
            @Override
            public void call(Object... args) {


                JSONObject data = (JSONObject) args[0];
//                data
                JSONObject jsonObject = new JSONObject();
                try {
                    JSONObject events = data.getJSONObject("data");
                    total_array = events.getJSONArray("all");

                    Log.e("total_array-1", "--" + total_array.length());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setData();

                            check_live_events();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void check_live_events() {

        local_socket.on("occurevent", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                JSONObject events = null;
                try {
                    events = data.getJSONObject("data");
//                    Log.e("occurevent_data", events.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                total_array.put(events);

//                Log.e("total_array", "--" + total_array.length());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                    }
                });


//                Log.e("occurevent", data.toString());
            }
        });
    }

    private Socket connectSocket(int i) {


        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.reconnection = true;
        opts.query = "token=" + loginPrefManager.getUserToken() + "&type_of_token=" + "user";
//        Log.e("opts", opts.query);
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

        local_socket.disconnect();
    }


    private void setData() {


        try {

            Log.e("setData", "---");

            selected = new JSONArray();
            for (int i = 0; i < total_array.length(); i++) {
                JSONObject events = total_array.getJSONObject(i);
                String device_type = events.getString("event_type");


                if (selected_event_type == 0) {

                    selected.put(total_array.get(i));
                }
                if (selected_event_type == 1 && device_type.equalsIgnoreCase("face")) {


                    selected.put(total_array.get(i));

                }
                if (selected_event_type == 2 && device_type.equalsIgnoreCase("motion")) {

                    selected.put(total_array.get(i));

                }
                if (selected_event_type == 3 && device_type.equalsIgnoreCase("device")) {

                    selected.put(total_array.get(i));
                }
            }
        } catch (JSONException e) {

        }


        eventAdapter = new EventAdapter(getActivity(), selected, new EventAdapter.EventsInterface() {
            @Override
            public void onEventSelect(JSONObject jsonObject) {

                selected_object = jsonObject;


                emit_single_event();
            }
        });
        events.setAdapter(eventAdapter);
        eventAdapter.notifyDataSetChanged();
    }

    private void emit_single_event() {

        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("event_id", selected_object.getString("_id"));
            jsonObject.put("product_id", selected_object.getString("product_id"));
            jsonObject.put("user_id", loginPrefManager.getUserId());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        local_socket.emit("eventReq", jsonObject);

        listen_event_data();

    }

    private void listen_event_data() {

        Log.e("listen_", "emit_single_");

        local_socket.on("eventRespond", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {

                        JSONObject jsonObject = (JSONObject) args[0];

                        try {
                            JSONObject data = jsonObject.getJSONObject("data");

                            String filedirectory = data.getString("filedirectory");

                            event_name = filedirectory;
                            event_video_url = APIServiceFactory.BASE_URL + filedirectory;

                            Log.e("file", filedirectory);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        showEventDialog();
                    }
                });

                Log.e("eventReq_data", data.toString());
            }
        });
    }

    private void showEventDialog() {

        eventDialog = new EventDialog(getActivity(), R.style.AppTheme, new EventDialog.EvetnInterface() {
            @Override
            public void onSubmit(boolean status, int position) {

                eventDialog.dismiss();

                if (position == 1) {

                    Intent intent = new Intent(getActivity(), PlayVidoeActivty.class);
                    intent.putExtra("url", event_video_url);
                    startActivity(intent);

                } else if (position == 2) {
                    downloadFile();
                }
            }
        });

        eventDialog.show();
    }

    public void downloadFile() {
        Uri uri = Uri.parse(event_video_url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, event_name);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
        request.allowScanningByMediaScanner();// if you want to be available from media players
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
