package com.app.homeycam.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.Login.User;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class UserActivty extends LocalizationActivity {


    TextView delete_tv, history;

    String homeImage_id, status, start, endTIme;

    CircleImageView user_pic;

    TextView start_time_tv, end_time_tv;

    JSONObject userObject;

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


        if (bundle != null) {

            try {
                userObject = new JSONObject(bundle.getString("object"));


                homeImage_id = userObject.getString("_id");

                if (userObject.has("settings")) {

                    JSONObject settings = userObject.getJSONObject("settings");
                    status = settings.getString("notification_status");
                    start = settings.getString("notification_start");
                    endTIme = settings.getString("notification_end");

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


            Log.e("byte", getFileToByte(filePath));
            user_pic.setImageBitmap(selectedImage);
        }
    }

    public static String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
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
}
