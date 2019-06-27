package com.app.homeycam.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.Register.Register;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.Register.RegisterData;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends LocalizationActivity {

    TextView signin_in;
    EditText email_edt, password_edt, confirm_edt;
    Button sign_up;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {

        signin_in = findViewById(R.id.sigin_in);
        email_edt = findViewById(R.id.email_text);
        password_edt = findViewById(R.id.password);
        confirm_edt = findViewById(R.id.confirm_password);
        sign_up = findViewById(R.id.sign_up);

        onClickEvents();
    }

    private void onClickEvents() {

        signin_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(RegisterActivity.this, LoginActivity.class, null);
                finish();
            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {

        if (!validEmail(email_edt)) {
            return;
        } else if (!validPassword(password_edt)) {
            return;
        } else if (!validConfirmPassword(password_edt, confirm_edt)) {
            return;
        }
        CallRegisterOnCall();
    }

    private void CallRegisterOnCall() {

        try {

            show_loader("Loading");

            RegisterData registerData = new RegisterData();
            registerData.setEmail(email_edt.getText().toString());
            registerData.setPassword(password_edt.getText().toString());
            registerData.setRole("admin");
            apiService.userRegisteronCall(registerData).enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {


                    dismiss_loader();

                    Log.e("response", new Gson().toJson(response));
                    if (getStatus(response.body().getStatus())) {

                        finish();

                        showShortMessage(response.body().getMessage());
                    } else {
                        showShortMessage(response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {


                    Log.e("onFailure", t.getMessage());

                    dismiss_loader();

                }
            });

        } catch (Exception e) {

            Log.e("Exception", e.getMessage());

            dismiss_loader();


        }
    }
}
