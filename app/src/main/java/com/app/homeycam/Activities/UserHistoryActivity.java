package com.app.homeycam.Activities;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

public class UserHistoryActivity extends LocalizationActivity {


    String home_image;

    TextView output_tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        home_image = getIntent().getStringExtra("home_id");

        output_tv = findViewById(R.id.output);
        getUserHistroy();
    }

    private void getUserHistroy() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("homeimage_id", home_image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("fetchHomeimage", jsonObject);

        getHistory();
    }

    private void getHistory() {


        local_socket.on("fetchHomeimage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];

                Log.e("jsonObject", jsonObject.toString());

                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        output_tv.setText(jsonObject.toString());
                    }
                });
            }
        });
    }
}
