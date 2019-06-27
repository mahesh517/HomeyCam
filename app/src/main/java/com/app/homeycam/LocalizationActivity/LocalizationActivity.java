package com.app.homeycam.LocalizationActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.homeycam.PermissionChecker.PermissionHelper;
import com.app.homeycam.ProgressBar.Loader;
import com.app.homeycam.R;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.app.homeycam.ServiceApi.ApiService;
import com.app.homeycam.Utils.ActivityHelper;
import com.app.homeycam.Utils.ActivityHelperImpl;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;


import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Locale;


public class LocalizationActivity extends AppCompatActivity implements OnLocaleChangedListener, ActivityHelper {

    private static final String KEY_ACTIVIY_LOCALE_CHANGED = "activity_locale_changed";
    private boolean isLocalizationChanged = false;
    private String currentLanguage = LanguageSetting.getDefaultLanguage();
    public ApiService apiService;
    public LoginPrefManager loginPrefManager;
    public ProgressDialog progressDialog;
    public Activity activity;
    public Loader loader;
    LocationManager mlocManager;
    public boolean location_enabled;
    public Snackbar snackbar;
    public PermissionHelper permissionHelper;
    private ActivityHelper ah = new ActivityHelperImpl(this);

    public Socket local_socket;
    private ActivityManager activityManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        Log.e("onCreate", "----");

        setupLanguage();
        checkBeforeLocaleChanging();
        super.onCreate(savedInstanceState);
        loginPrefManager = new LoginPrefManager(LocalizationActivity.this);
        progressDialog = new ProgressDialog(LocalizationActivity.this);
        loader = new Loader(LocalizationActivity.this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        apiService = APIServiceFactory.getRetrofit().create(ApiService.class);
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        location_enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        activityManager = (ActivityManager) getApplication().getSystemService(Activity.ACTIVITY_SERVICE);
        permissionHelper = new PermissionHelper(LocalizationActivity.this);

//        connectSocket(1);
//
        if (local_socket != null) {
            if (local_socket.connected()) {
            } else {
                connectSocket(1);
            }
        } else {

            connectSocket(1);
        }


    }

    // Provide method to set application language by country name.
    protected final void setLanguage(String language) {
        if (!isDuplicatedLanguageSetting(language)) {
            LanguageSetting.setLanguage(LocalizationActivity.this, language);
            notifyLanguageChanged();
        }
    }

    // Provide method to set application language by locale.
    public final void setLanguage(Locale locale) {
        setLanguage(locale.getLanguage());
    }

    // Get current language
    protected final String getLanguage() {
        return LanguageSetting.getLanguage();
    }

    // Check that bundle come from locale change.
    // If yes, bundle will obe remove and set boolean flag to "true".
    private void checkBeforeLocaleChanging() {
        boolean isLocalizationChanged = getIntent().getBooleanExtra(KEY_ACTIVIY_LOCALE_CHANGED, false);
        if (isLocalizationChanged) {
            this.isLocalizationChanged = true;
            getIntent().removeExtra(KEY_ACTIVIY_LOCALE_CHANGED);
        }
    }


    private void setupLanguage() {
        Locale locale = LanguageSetting.getLocale(LocalizationActivity.this);
        setupLocale(locale);
        currentLanguage = locale.getLanguage();
        LanguageSetting.setLanguage(LocalizationActivity.this, locale.getLanguage());
    }

    private void setupLocale(Locale locale) {
        updateLocaleConfiguration(LocalizationActivity.this, locale);
    }


    private void updateLocaleConfiguration(Context context, Locale locale) {
        Configuration config = new Configuration();
        config.locale = locale;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        context.getResources().updateConfiguration(config, dm);
    }


    private boolean isDuplicatedLanguageSetting(String language) {
        return language.toLowerCase(Locale.getDefault()).equals(LanguageSetting.getLanguage());
    }


    private void notifyLanguageChanged() {
        onBeforeLocaleChanged();
        getIntent().putExtra(KEY_ACTIVIY_LOCALE_CHANGED, true);
        callDummyActivity();
        recreate();
    }


    @Override
    public void onResume() {
        super.onResume();

        new Handler().post(() -> {
            checkLocaleChange();
            checkAfterLocaleChanging();
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (local_socket != null) {
            local_socket.disconnect();
        }

        dismiss_loader();
    }

    private void checkLocaleChange() {
        if (!LanguageSetting.getLanguage().toLowerCase(Locale.getDefault())
                .equals(currentLanguage.toLowerCase(Locale.getDefault()))) {
            callDummyActivity();
            recreate();
        }
    }


    private void checkAfterLocaleChanging() {
        if (isLocalizationChanged) {
            onAfterLocaleChanged();
            isLocalizationChanged = false;
        }
    }


    private void callDummyActivity() {
        startActivity(new Intent(LocalizationActivity.this, BlankDummyActivity.class));
    }


    @Override
    public void onBeforeLocaleChanged() {
    }


    @Override
    public void onAfterLocaleChanged() {
    }

    public void showShortToastMessage(String message) {
        Toast.makeText(LocalizationActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showShortMessage(String msg) {


        Toast.makeText(LocalizationActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


    public void show_loader(String message) {
        try {
            if (!isFinishing()) {
                if (loader != null && !loader.isShowing()) {
                    loader.show();
                    loader.setMessage(message);
                }
            }
        } catch (Exception e) {

            Log.e("exception", e.getMessage());
            e.printStackTrace();
        }
    }

    public void dismiss_loader() {
        try {
            if (!isFinishing()) {
                if (loader != null) {
                    loader.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validEmail(EditText email_edit) {

        String email = email_edit.getText().toString();

        if (email.equalsIgnoreCase("")) {

            showShortMessage(getString(R.string.error_valid_message));


            return false;
        } else {
            email_edit.setError(null);
        }

        return true;
    }

    public boolean validMobileno(EditText mobile_text) {

        String phone_no = mobile_text.getText().toString();

        if (phone_no.isEmpty() || phone_no.length() < 0) {


            showShortMessage(getString(R.string.error_valid_message));
            return false;
        } else {
            mobile_text.setError(null);
        }

        return true;
    }

    public boolean validFirstName(EditText mobile_text) {

        String phone_no = mobile_text.getText().toString();

        if (phone_no.isEmpty() || phone_no.length() < 0) {


            showShortMessage(getString(R.string.error_valid_message));
            return false;
        } else {
            mobile_text.setError(null);
        }

        return true;
    }

    public boolean validadob(TextView dob) {

        String phone_no = dob.getText().toString();

        if (phone_no.isEmpty() || phone_no.length() < 0) {
            showShortMessage(getString(R.string.error_valid_message));
            return false;
        } else {
            dob.setError(null);
        }

        return true;
    }

    public boolean validLastName(EditText mobile_text) {

        String phone_no = mobile_text.getText().toString();

        if (phone_no.isEmpty() || phone_no.length() < 0) {
            showShortMessage(getString(R.string.error_valid_message));
            return false;
        } else {
            mobile_text.setError(null);
        }

        return true;
    }


    public boolean validPassword(EditText password_edit) {

        String password = password_edit.getText().toString();
        if (password.isEmpty() || password.length() < 0) {

            showShortMessage(getString(R.string.error_valid_message));
            return false;

        } else {
            password_edit.setError(null);
        }

        return true;
    }

    public boolean validConfirmPassword(EditText new_password, EditText confirm_password) {


        if (confirm_password.getText().toString().equalsIgnoreCase("")) {
            showShortMessage(getString(R.string.error_valid_message));
            return false;
        }
        if (!new_password.getText().toString().equals(confirm_password.getText().toString())) {

            confirm_password.setError("Passwords are mis matching");
            return false;
        }


        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void hideKeyboard(View view) {

    }

    @Override
    public Typeface createTypeFace(String fontName) {
        return null;
    }

    @Override
    public Typeface createTypeFace(Context context, String fontName) {
        return null;
    }

    @Override
    public Drawable createRepeatableDrawable(int imageId) {
        return null;
    }

    @Override
    public boolean isNetworkAvailable(Activity activity) {
        return false;
    }

    @Override
    public void switchToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {

    }

    @Override
    public void goToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {
        ah.goToActivity(current, otherActivityClass, extras);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void setListeners() {

    }

    public boolean getStatus(String message) {

        if (message.equalsIgnoreCase("notok")) {
            return false;
        }
        return true;
    }

    private Socket connectSocket(int i) {

        ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(26).get(0);

        IO.Options opts = new IO.Options();
        opts.query = "token=" + loginPrefManager.getUserToken() + "&type_of_token=" + "user";
        opts.reconnection = true;
        opts.forceNew = true;
        try {
            local_socket = IO.socket(APIServiceFactory.BASE_URL, opts);

            if (!runningTaskInfo.topActivity.getClassName().equals("com.app.homeycam.Activities.SplashScreenActivity") & !loginPrefManager.getUserToken().equalsIgnoreCase("")) {
                local_socket.connect();

                local_socket.on("auth", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.e("auth_data:", new Gson().toJson(args));
                    }
                });
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return local_socket;
    }


}
