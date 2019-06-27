package com.app.homeycam.Activities;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Magnifier;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.homeycam.CustomeViews.ZoomLayout;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.Login.Data;
import com.app.homeycam.ModelClass.Login.Login;
import com.app.homeycam.ModelClass.Login.ProductAccess;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.Login.LoginData;
import com.google.gson.Gson;

import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends LocalizationActivity {


    EditText email_edt, password_edt;

    Button get_start_btn;
    TextView create_account;
    int zoom_position = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();


    }

    private void fadeview() {

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        email_edt.startAnimation(fadeIn);
        email_edt.startAnimation(fadeOut);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1200);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(4200 + fadeIn.getStartOffset());
    }

    private void initView() {

        create_account = findViewById(R.id.create_account);
        email_edt = findViewById(R.id.email_text);
        password_edt = findViewById(R.id.password);
        get_start_btn = findViewById(R.id.get_start);


        onClickEvents();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onClickEvents() {


        email_edt.setOnTouchListener((view, motionEvent) -> {


            if (zoom_position == 2) {
                Animation animNormal = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_normal);
//                password_edt.startAnimation(animNormal);
            }
            zoom_position = 1;

            Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//            email_edt.startAnimation(animZoomIn);
            return false;
        });
        password_edt.setOnTouchListener((view, motionEvent) -> {

            if (zoom_position == 1) {
                Animation animNormal = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_normal);
//                email_edt.startAnimation(animNormal);
            }

            zoom_position = 2;

            Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//            password_edt.startAnimation(animZoomIn);
            return false;
        });


        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToActivity(LoginActivity.this, RegisterActivity.class, null);
            }
        });

        get_start_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                validateData();

//                Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//                Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
////                email_edt.startAnimation(animZoomIn);
//                Magnifier magnifier = new Magnifier(email_edt);

            }
        });
    }

    private void validateData() {

        if (!validEmail(email_edt)) {
            return;
        }
        if (!validPassword(password_edt)) {
            return;
        }


//        String[] timeZone = TimeZone.getAvailableIDs();
//
//        for (String timeZone1 : timeZone) {
//            Log.e("timezone", timeZone1);
//        }

        LoginonCall();


    }

    private void LoginonCall() {

        try {

            show_loader("Loading");

            LoginData loginData = new LoginData();
            loginData.setEmail(email_edt.getText().toString());
            loginData.setPassword(password_edt.getText().toString());
            loginData.setDevice_id(loginPrefManager.getDeviceToken());
            loginData.setPlatform("android");
            loginData.setRole("admin");

            Log.e("loginData", new Gson().toJson(loginData));
            apiService.loginonCall(loginData).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {


                    dismiss_loader();

                    Log.e("response", new Gson().toJson(response.body()));
                    if (getStatus(response.body().getStatus())) {

                        showShortMessage(response.body().getMessage());


                        Data data = response.body().getData();
                        loginPrefManager.setUserToken(response.body().getToken());
                        loginPrefManager.setUserId(data.getUser().getId());
                        loginPrefManager.setStringValue("user_email", email_edt.getText().toString());


                        List<ProductAccess> productAccesses = data.getUser().getProductAccess();
                        if (productAccesses != null && productAccesses.size() > 0) {
                            ProductAccess productAccess = productAccesses.get(0);

                            loginPrefManager.setStringValue("product_id", productAccess.getProductId());

                            goToActivity(LoginActivity.this, DashBoard.class, null);
                        } else {
                            goToActivity(LoginActivity.this, HomeActivity.class, null);
                        }


                        ActivityCompat.finishAffinity(LoginActivity.this);
                        finish();
                    } else {

                        showShortMessage(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    dismiss_loader();

                    Log.e("onFailure", t.getMessage());
                }
            });

        } catch (Exception e) {

            dismiss_loader();
            Log.e("Exception", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {

        ActivityCompat.finishAffinity(this);
        finish();
        super.onBackPressed();
    }


    public void zoom(Float scaleX, Float scaleY, PointF pivot, EditText view) {
        view.setPivotX(pivot.x);
        view.setPivotY(pivot.y);
        view.setScaleX(scaleX);
        view.setScaleY(scaleY);
    }
}
