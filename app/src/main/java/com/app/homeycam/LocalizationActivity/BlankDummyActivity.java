package com.app.homeycam.LocalizationActivity;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.app.homeycam.R;


public class BlankDummyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.animation_localization_activity_transition_in,
                R.anim.animation_localization_activity_transition_out);
        setContentView(R.layout.activity_blank_dummy);

        dalayedFinish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.animation_localization_activity_transition_in,
                R.anim.animation_localization_activity_transition_out);
    }

    private void dalayedFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 200);
    }
}
