package com.app.homeycam.Activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.app.homeycam.R;

public class LiveVideoActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private TextView mTextMessage;


    private VideoView videoView;
    MediaController mediaController;

    ImageView mp_c;

    boolean video_status = false;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_video);

        videoView = findViewById(R.id.video_view);
        mp_c = findViewById(R.id.media_controller);

        handler = new Handler();

        videoView.setOnPreparedListener(this);


        videoView.setVideoPath("http://35.231.3.136:3300/video/5cee219f2c1393271b500b9d/live/temp.m3u8");


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
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                /*
                 * add media controller
                 */
                mediaController = new MediaController(LiveVideoActivity.this);
//                videoView.setMediaController(mediaController);
                /*
                 * and set its position on screen
                 */
//                mediaController.setAnchorView(videoView);
            }
        });


    }


}
