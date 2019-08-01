package com.app.homeycam.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.Settings.FaceSettings;
import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.Settings.FaceNotifications;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.bumptech.glide.Glide;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivty extends LocalizationActivity {


    TextView delete_tv, history;

    String homeImage_id, status, start, start_total_time, end_total_time, endTIme, person_name;

    CircleImageView user_pic;

    TextView start_time_tv, end_time_tv, user_name;

    JSONObject userObject;

    int timer_position = 0;

    int start_hour, start_min, end_hour, end_min;

    SwitchButton switch_button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activty);


        Bundle bundle = getIntent().getExtras();

        delete_tv = findViewById(R.id.delete);
        user_pic = findViewById(R.id.user_pic);
        start_time_tv = findViewById(R.id.from_time);
        end_time_tv = findViewById(R.id.to_time);
        history = findViewById(R.id.history);
        user_name = findViewById(R.id.user_name);

        switch_button = findViewById(R.id.switch_button);


        if (bundle != null) {

            try {
                userObject = new JSONObject(bundle.getString("object"));

//                Log.e("userObject", userObject.toString());

                homeImage_id = userObject.getString("_id");


                Log.e("homeImage", homeImage_id);

                if (userObject.has("person_name")) {

                    person_name = userObject.getString("person_name");
                    user_name.setText(person_name);
//                    Log.e("userObject", userObject.getString("person_name"));
                }

                if (userObject.has("image_selected_check")) {

                    JSONArray jsonArray = userObject.getJSONArray("image_selected_check");

                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.has("file_name")) {
                        String image_url = jsonObject.getString("file_name");

                        image_url = APIServiceFactory.IMAGE_URL + image_url;

                        Log.e("image_url", image_url);

                        Glide.with(UserActivty.this).load(image_url).into(user_pic);
                    }


                }

                if (userObject.has("settings")) {


                    JSONObject settings = userObject.getJSONObject("settings");

                    Log.e("settings", settings.toString());

                    status = settings.getString("notification_status");
                    start = settings.getString("notification_start");
                    endTIme = settings.getString("notification_end");


                    String[] start_hours = start.split(":");
                    String[] end_hours = start.split(":");

                    start_hour = Integer.parseInt(start_hours[0]);
                    start_min = Integer.parseInt(start_hours[1]);
                    end_hour = Integer.parseInt(end_hours[0]);
                    end_min = Integer.parseInt(end_hours[1]);

                    if (start_hour >= 12) {
                        start_total_time = start + " P.M";
                    } else if (start_hour < 12) {
                        start_total_time = start + " A.M";

                    }
                    if (end_hour >= 12) {
                        end_total_time = endTIme + " P.M";
                    } else if (end_hour < 12) {
                        end_total_time = endTIme + " A.M";

                    }

                    start_time_tv.setText(start);
                    end_time_tv.setText(endTIme);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteface();
            }
        });

        onclick();
    }

    private void onclick() {


        start_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer_position = 1;
                showTImerPicker(start_time_tv);
            }
        });
        end_time_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer_position = 2;
                showTImerPicker(end_time_tv);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserActivty.this, UserHistoryActivity.class).putExtra("home_id", homeImage_id));
            }
        });

        user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivty.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);
            }
        });

        switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                updateNotifications();
            }
        });
    }

    private void showTImerPicker(TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();


        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        if (timer_position == 2) {


            String[] start_hours = start.split(":");

            hour = Integer.parseInt(start_hours[0]);
            minute = Integer.parseInt(start_hours[1]);

        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(UserActivty.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if (timer_position == 1) {

                            if (hourOfDay >= 12) {
                                start_total_time = hourOfDay + ":" + minute + " P.M";
                            } else {
                                start_total_time = hourOfDay + ":" + minute + " A.M";
                            }
                        }
                        if (timer_position == 2) {
                            if (hourOfDay >= 12) {
                                end_total_time = hourOfDay + ":" + minute + "P.M";
                            } else {
                                end_total_time = hourOfDay + ":" + minute + "A.M";
                            }
                        }


                        textView.setText(hourOfDay + ":" + minute);

                        updateNotifications();
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    private void deleteface() {

        JSONObject deltedObject = new JSONObject();


        try {
            deltedObject.put("homeimage_id", homeImage_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("delete", deltedObject.toString());
        local_socket.emit("deleteFace", deltedObject);

        listendeleteface();

    }

    private void listendeleteface() {
        local_socket.on("deleteFace", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];

                Log.e("jsonObject", jsonObject.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);


            user_pic.setImageBitmap(selectedImage);

            updateImage(getFileToByte(filePath));
        }
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp;
        ByteArrayOutputStream bos;
        byte[] bt;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeString;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        finish();
    }

    private void updateImage(String fileToByte) {

        JSONObject jsonObject = new JSONObject();

        try {
//            jsonObject.put("product_id", loginPrefManager.getStringValue("product_id"));
////            jsonObject.put("type_of_image", "single");
////            jsonObject.put("person_name", person_name);
            jsonObject.put("buffer", fileToByte);
            jsonObject.put("homeimage_id", homeImage_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("faceRegistration", jsonObject.toString());

        local_socket.emit("faceAddingFromGallery", jsonObject);
    }

    private void listenUpdate_image() {

        local_socket.on("faceAddingFromGallery", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];
                Log.e("face_o/p", jsonObject.toString());
                Handler mHandler = new Handler(Looper.getMainLooper());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        loginPrefManager.setStringValue("live_update", "0");
                        finish();
                    }
                });

            }
        });

    }


    private void updateNotifications() {

        FaceNotifications faceNotifications = new FaceNotifications();

        faceNotifications.setHomeimage_id(homeImage_id);

        faceNotifications.setNotification_start(start_total_time);
        faceNotifications.setNotification_end(end_total_time);


        if (switch_button.isChecked()) {
            faceNotifications.setNotification_status("on");
        } else {
            faceNotifications.setNotification_status("off");
        }


        apiService.updateFacesettings(faceNotifications).enqueue(new Callback<FaceSettings>() {
            @Override
            public void onResponse(Call<FaceSettings> call, Response<FaceSettings> response) {
                loginPrefManager.setStringValue("live_update", "0");
            }

            @Override
            public void onFailure(Call<FaceSettings> call, Throwable t) {

            }
        });

    }


}
