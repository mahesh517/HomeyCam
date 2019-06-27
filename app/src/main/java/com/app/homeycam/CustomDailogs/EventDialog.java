package com.app.homeycam.CustomDailogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.homeycam.R;

public class EventDialog extends Dialog {

    Context context;

    TextView cancel_tv, play_video, download_video;

    EvetnInterface alertInterface;

    public EventDialog(Context context, int appTheme, EvetnInterface alertInterface) {
        super(context, appTheme);
        this.context = context;
        this.alertInterface = alertInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.event_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        super.onCreate(savedInstanceState);


        cancel_tv = findViewById(R.id.btn_cancel);
        play_video = findViewById(R.id.play_video);
        download_video = findViewById(R.id.download_video);


        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertInterface != null) {
                    alertInterface.onSubmit(true, 1);
                }
            }
        });
        download_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertInterface != null) {
                    alertInterface.onSubmit(true, 2);
                }
            }
        });
    }


    public interface EvetnInterface {
        void onSubmit(boolean status, int position);
    }
}