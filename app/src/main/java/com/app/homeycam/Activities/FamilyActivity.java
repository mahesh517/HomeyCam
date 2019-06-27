package com.app.homeycam.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.app.homeycam.Adapters.FacesAdapter;
import com.app.homeycam.Adapters.FamilyAdapter;
import com.app.homeycam.CustomDailogs.NewNameDialog;
import com.app.homeycam.CustomeViews.SimpleDividerItemDecoration;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class FamilyActivity extends LocalizationActivity {

    FamilyAdapter familyAdapter;

    RecyclerView familyMember;

    FloatingActionButton add_member;

    NewNameDialog newNameDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);


        familyMember = findViewById(R.id.family_view);


        add_member = findViewById(R.id.add_member);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FamilyActivity.this);

        familyMember.setLayoutManager(new LinearLayoutManager(FamilyActivity.this));

        familyMember.addItemDecoration(new SimpleDividerItemDecoration(FamilyActivity.this));

        getFamilyMembers();

        onClickevents();
    }

    private void onClickevents() {

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FamilyActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);
            }
        });
    }

    private void getFamilyMembers() {

        JSONObject familyObject = new JSONObject();

        try {
            familyObject.put("product_id", loginPrefManager.getStringValue("product_id"));
            familyObject.put("knownfaces", "false");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        local_socket.emit("getalltheKnownFaces", familyObject);

        listenFamilymembers();
    }

    private void listenFamilymembers() {

        local_socket.on("getalltheKnownFaces", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];
//                Log.e("json", jsonObject.toString());

                try {
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray know_array = data.getJSONArray("knownfaces");

                    for (int i = 0; i < know_array.length(); i++) {
                        JSONObject innerObj = know_array.getJSONObject(i);


                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            familyAdapter = new FamilyAdapter(FamilyActivity.this, 2, know_array, new FamilyAdapter.FaceInterface() {
                                @Override
                                public void onFaceclicked(String jsonObject1) {

                                    if (jsonObject1 != null) {

                                        Bundle bundle = new Bundle();

                                        bundle.putString("object", jsonObject1);
                                        goToActivity(FamilyActivity.this, UserActivty.class, bundle);
                                    }

                                }
                            });

                            familyMember.setAdapter(familyAdapter);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);

            showNameDailog(getFileToByte(filePath));
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

    private void addNewface(String encodeString, String msg) {
        JSONObject faceObject = new JSONObject();

        try {
            faceObject.put("type_of_image", "single");
            faceObject.put("buffer", encodeString);
            faceObject.put("person_name", msg);
            faceObject.put("product_id", loginPrefManager.getStringValue("product_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("faceObject", faceObject.toString());
        local_socket.emit("faceRegistrationFromGallery", faceObject);
        listenRegistration();
        getFamilyMembers();


    }

    private void listenRegistration() {


        local_socket.on("faceRegistrationFromGallery", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject jsonObject = (JSONObject) args[0];


                Log.e("face_reg", jsonObject.toString());
            }
        });
    }


    private void showNameDailog(String encodeString) {

        newNameDialog = new NewNameDialog(FamilyActivity.this, R.style.AppTheme, 1, new NewNameDialog.CancelInterface() {
            @Override
            public void buttonselected(boolean status, String msg) {

                newNameDialog.dismiss();

                if (status) {
                    addNewface(encodeString, msg);
                }
            }
        });

        newNameDialog.show();
    }


}
