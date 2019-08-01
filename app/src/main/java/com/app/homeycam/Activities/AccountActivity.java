package com.app.homeycam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.app.homeycam.CustomDailogs.ChangePasswordDialog;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.PasswordApi.PasswordUpdate;
import com.app.homeycam.ModelClass.UserDetails.User;
import com.app.homeycam.ModelClass.UserDetails.UserView;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.ForgotPassword.ChangePasswordData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends LocalizationActivity {

    TextView email;

    TextView change_password;

    ChangePasswordDialog changePasswordDialog;

    ChangePasswordData changePasswordData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        email = findViewById(R.id.email);

        getUserDetails();
    }

    private void getUserDetails() {


        try {

            show_loader("Loading");
            apiService.getUserDetails(loginPrefManager.getUserId()).enqueue(new Callback<UserView>() {
                @Override
                public void onResponse(Call<UserView> call, Response<UserView> response) {

                    dismiss_loader();

                    User user = response.body().getData().getUser();

                    email.setText(user.getEmail());


                }

                @Override
                public void onFailure(Call<UserView> call, Throwable t) {

                }
            });

        } catch (Exception e) {

        }
    }

    private void show_password_dialog() {
        changePasswordDialog = new ChangePasswordDialog(AccountActivity.this, R.style.AppTheme, new ChangePasswordDialog.PasswordInterface() {
            @Override
            public void onItemClick(boolean status, String old, String newPass) {

                changePasswordData = new ChangePasswordData();
                changePasswordData.setUser_id(loginPrefManager.getUserId());
                changePasswordData.setOld_password(old);
                changePasswordData.setNew_password(newPass);

                updatePassword();

            }
        });

        changePasswordDialog.show();
    }

    private void updatePassword() {

        try {

            show_loader("Updating");
            apiService.updatePassword(loginPrefManager.getUserToken(), changePasswordData).enqueue(new Callback<PasswordUpdate>() {
                @Override
                public void onResponse(Call<PasswordUpdate> call, Response<PasswordUpdate> response) {

                    dismiss_loader();
                    showShortMessage(response.body().getMessage());
                }

                @Override
                public void onFailure(Call<PasswordUpdate> call, Throwable t) {

                    dismiss_loader();
                    showShortMessage(t.getMessage());
                }
            });

        } catch (Exception e) {
            dismiss_loader();
        }
    }
}
