package com.app.homeycam.Activities;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.app.homeycam.R;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

public class PlayVidoeActivty extends AppCompatActivity implements OnPreparedListener {

    private VideoView videoView;

    private String videourl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vidoe_activty);

        videourl = getIntent().getStringExtra("url");

        setupVideoView();
    }

    private void setupVideoView() {
        videoView = findViewById(R.id.video_view);
        videoView.setOnPreparedListener(this);

        videoView.setVideoURI(Uri.parse(videourl));

    }

    @Override
    public void onPrepared() {
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
