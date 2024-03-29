package com.app.homeycam.ProgressBar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.homeycam.R;


public class Loader extends Dialog {
    Context context;


    public ImageView loaderSpinner;

    TextView message_tv;

    private static final int ROTATE_ANIMATION_DURATION = 3000;

    public Loader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progressbar);
        this.setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        message_tv = findViewById(R.id.message);

    }


    public void setMessage(String message) {
        message_tv.setText(message);
    }

    @SuppressLint("LongLogTag")
    public void startAnimation() {

        Log.e("ROTATE_ANIMATION_DURATION", "--" + ROTATE_ANIMATION_DURATION);

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(5000);

        rotate.setInterpolator(new LinearInterpolator());

        rotate.setRepeatCount(Animation.INFINITE);

        loaderSpinner.setAnimation(rotate);
    }

    @Override
    public void onBackPressed() {
    }

    public void clear() {

        if (loaderSpinner != null) {
            loaderSpinner.clearAnimation();
        }

    }
}
