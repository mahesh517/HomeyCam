package com.app.homeycam.Fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.homeycam.Activities.FamilyActivity;
import com.app.homeycam.Activities.UserActivty;
import com.app.homeycam.Adapters.FacesAdapter;
import com.app.homeycam.CustomDailogs.AddMemberDialog;
import com.app.homeycam.CustomDailogs.NewNameDialog;
import com.app.homeycam.CustomeViews.SpacesItemDecoration;
import com.app.homeycam.R;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adib on 13-Apr-17.
 */

public class LiveFragment extends BaseFragment implements MediaPlayer.OnPreparedListener {


    ImageView imageView;
    TextView data;

    FrameLayout video_player_view;

    private VideoView videoView;
    MediaController mediaController;

    ImageView mp_c;

    boolean video_status = false;

    Handler handler;

    FacesAdapter facesAdapter;

    RecyclerView recyclerView;
    private OnFragmentInteractionListener listener;

    List<String> name_list;


    AddMemberDialog addMemberDialog;

    NewNameDialog newNameDialog;

    String home_face_id, newName;

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);


        imageView = view.findViewById(R.id.video_view);
        video_player_view = view.findViewById(R.id.video_player_view);


        videoView = view.findViewById(R.id.live_video);
        mp_c = view.findViewById(R.id.media_controller);

        recyclerView = view.findViewById(R.id.face_view);

        recyclerView.setHasFixedSize(true);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.small_margin);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));

        handler = new Handler();

        videoView.setOnPreparedListener(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (local_socket != null) {
                    checkLiveVideo();
                }

            }
        });


        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!video_status) {

                    mp_c.setVisibility(View.VISIBLE);
                    mp_c.setImageDrawable(getResources().getDrawable(R.drawable.pause_round));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mp_c.setVisibility(View.GONE);

                        }
                    }, 1000);


                } else {
                    video_status = false;
                    mp_c.setVisibility(View.VISIBLE);
                    videoView.pause();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mp_c.setVisibility(View.GONE);

                        }
                    }, 1000);
                    mp_c.setImageDrawable(getResources().getDrawable(R.drawable.play_round));
                }
            }
        });

        mp_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!video_status) {
                    video_status = true;
                    videoView.start();
                    mp_c.setVisibility(View.VISIBLE);
                    mp_c.setImageDrawable(getResources().getDrawable(R.drawable.pause_round));
                } else {
                    video_status = false;
                    mp_c.setVisibility(View.VISIBLE);
                    videoView.pause();
                    mp_c.setImageDrawable(getResources().getDrawable(R.drawable.play_round));
                }
            }
        });

        return view;
    }

    public void emitallfaces() {

        if (local_socket != null) {

            show_loader("Loading");


            JSONObject faces_object = new JSONObject();

            try {
                faces_object.put("product_id", loginPrefManager.getStringValue("product_id"));
//                faces_object.put("knownfaces", "false");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            local_socket.emit("fetchall", faces_object);

            getAllfaces();
        }
    }

    private void getAllfaces() {


        if (local_socket != null) {
            local_socket.on("fetchall", new Emitter.Listener() {
                @Override
                public void call(Object... args) {

                    JSONObject jsonObject = (JSONObject) args[0];

//                    Log.e("fetchall", jsonObject.toString());
                    try {
                        JSONObject data = jsonObject.getJSONObject("data");

                        name_list = new ArrayList<>();
                        JSONArray knownfaces = data.getJSONArray("home");

                        for (int i = 0; i < knownfaces.length(); i++) {
                            JSONObject innerObj = knownfaces.getJSONObject(i);


                            String jsonObject2 = innerObj.getString("last_seen");
                            String name = null;
                            if (innerObj.has("person_name")) {
                                name = innerObj.getString("person_name");
                                name_list.add(name);
                            }


                            JSONArray jsonObject1 = innerObj.getJSONArray("image_details");


                        }

                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                facesAdapter = new FacesAdapter(getActivity(), 1, knownfaces, new FacesAdapter.FaceInterface() {
                                    @Override
                                    public void onFaceclicked(boolean name_status, String id, String jsonObject1) {

                                        home_face_id = id;

                                        if (!name_status) {
                                            show_add_memberDialog();
                                        } else {

                                            if (jsonObject1 != null) {
                                                Bundle bundle = new Bundle();

                                                bundle.putString("object", jsonObject1);

                                                Intent intent = new Intent(getActivity(), UserActivty.class);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        }

                                    }
                                });

                                recyclerView.setAdapter(facesAdapter);

                                dismiss_loader();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }


    private void checkLiveVideo() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("status", "start");
            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        local_socket.emit("ondeamandstream", jsonObject);
        getLiveVideo();

    }

    private void getLiveVideo() {

        local_socket.on("ondeamandstream", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];


                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String url = jsonObject1.getString("filepath");


                            url = APIServiceFactory.BASE_URL + url;

                            video_player_view.setVisibility(View.VISIBLE);
                            imageView.setVisibility(View.GONE);
                            videoView.setVideoPath(url);
                            videoView.start();

                            Log.e("file_path", url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("jsonException", e.getMessage());
                        }

                    }
                });


                Log.e("jsonObject", jsonObject.toString());


            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsFragment.OnFragmentInteractionListener) {
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

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                mediaController = new MediaController(getActivity());
            }
        });
    }

    public interface OnFragmentInteractionListener {

        void onClicked();

    }

    private void show_add_memberDialog() {

        addMemberDialog = new AddMemberDialog(getActivity(), R.style.AppTheme, name_list, new AddMemberDialog.MemberInterface() {
            @Override
            public void onNameAdded(int position, String name) {

                addMemberDialog.dismiss();
                if (position == 0) {
                    show_new_nameDialog();
                } else if (position == 1) {
//                    updateName(name);
                }

            }
        });

        addMemberDialog.setCanceledOnTouchOutside(true);
        addMemberDialog.show();
    }


    private void show_new_nameDialog() {
        newNameDialog = new NewNameDialog(getActivity(), R.style.AppTheme, 1, new NewNameDialog.CancelInterface() {
            @Override
            public void buttonselected(boolean status, String msg) {

                newNameDialog.dismiss();
                if (status) {
//                    updateName(msg);
                }
            }
        });

        newNameDialog.show();
    }

    private void updateName(String msg) {
        JSONObject home_face_object = new JSONObject();

        try {
            home_face_object.put("homeface_id", home_face_id);
            home_face_object.put("person_name", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("updatename", home_face_object);
        listenupdate();

    }

    private void listenupdate() {


        local_socket.on("updatename", new Emitter.Listener() {
            @Override
            public void call(Object... args) {


                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getAllfaces();
                    }
                });

            }
        });

    }

}
