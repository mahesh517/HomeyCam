package com.app.homeycam.Activities;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.homeycam.CustomDailogs.WifiStartDialog;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class HomeActivity extends LocalizationActivity {

    LinearLayout add_view, slide_one_view, slide_second_view, slide_third, qr_code_view;

    TextView slide_one_next, slide_second_next;

    EditText wifi_password, cam_location;

    ImageView qr_code_iv;

    int position = 0;

    Spinner wifi_list;

    StringBuilder sb = new StringBuilder();
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;

    String TAG = "HOMEACTIVTY";

    List<String> wifi_name;

    int QRcodeWidth = 350;
    Bitmap bitmap;
    WifiStartDialog wifiStartDialog;

    QRGEncoder qrgEncoder;

    TimePickerDialog timePickerDialog;

    private Socket mSocket;

    String last_activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initWifi();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        last_activity = loginPrefManager.getWifistatus();

        Log.e("last_activity", last_activity);

        initView();
    }

    private void initWifi() {

        mainWifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        if (mainWifi.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            mainWifi.setWifiEnabled(true);
        }

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mainWifi.startScan();


    }


    private void initView() {

        add_view = findViewById(R.id.add_view);
        slide_one_view = findViewById(R.id.slide_one_view);
        slide_second_view = findViewById(R.id.slide_second);
        slide_third = findViewById(R.id.slide_three);
        slide_one_next = findViewById(R.id.next_one_tv);
        slide_second_next = findViewById(R.id.confirm_details);
        wifi_list = findViewById(R.id.wifi_edt);
        qr_code_iv = findViewById(R.id.qr_code);
        qr_code_view = findViewById(R.id.qr_code_view);

        wifi_password = findViewById(R.id.wifi_password_edt);
        cam_location = findViewById(R.id.location_edt);

        initScreens();
        onClickevnets();

    }

    private void initScreens() {


        if (last_activity.equalsIgnoreCase("change")) {

            slide_one_view.setVisibility(View.GONE);
            slide_second_view.setVisibility(View.GONE);
            slide_third.setVisibility(View.VISIBLE);

            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults();

            wifi_name = new ArrayList<>();

            for (int i = 0; i < wifiList.size(); i++) {
                sb.append(new Integer(i + 1).toString() + ".");
                sb.append((wifiList.get(i)).SSID);
                sb.append("\n");
                wifi_name.add(wifiList.get(i).SSID);
            }

            Log.e("wifi_name", new Gson().toJson(wifi_name));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, wifi_name);

            wifi_list.setAdapter(adapter);
        }


    }

    private void onClickevnets() {


        add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add_view.setVisibility(View.GONE);
                slide_one_view.setVisibility(View.VISIBLE);
            }
        });
        slide_one_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slide_one_view.setVisibility(View.GONE);
                slide_second_view.setVisibility(View.VISIBLE);

                showWifiDialog();
            }
        });


        slide_second_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validFirstName(wifi_password)) {
                    return;
                }
                if (!validLastName(cam_location)) {
                    return;
                }

                TimeZone timeZone = TimeZone.getDefault();


                Log.e("timeZone", timeZone.getID());

                StringBuilder stringBuilder = new StringBuilder("");

                stringBuilder.append(wifi_list.getSelectedItem().toString());
                stringBuilder.append(",");
                stringBuilder.append(wifi_password.getText().toString());
                stringBuilder.append(",");
                stringBuilder.append(cam_location.getText().toString());
                stringBuilder.append(",");
                stringBuilder.append(loginPrefManager.getStringValue("user_email"));
                stringBuilder.append(",");
                stringBuilder.append(loginPrefManager.getUserId());
                stringBuilder.append(",");
                stringBuilder.append(loginPrefManager.getWifistatus());
                stringBuilder.append(",");
                stringBuilder.append(timeZone.getID());

//                reset ,password ----change
//                timezone
                Log.e("string", "--" + stringBuilder.toString());

                show_loader("Loading");


                Handler handler = new Handler();
                handler.postDelayed(() -> {

                    loader.dismiss();
                    generateQrCode(stringBuilder.toString());
                }, 1000);


            }
        });
    }


    class WifiReceiver extends BroadcastReceiver {

        public void onReceive(Context c, Intent intent) {

            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults();

            wifi_name = new ArrayList<>();

            for (int i = 0; i < wifiList.size(); i++) {
                sb.append(new Integer(i + 1).toString() + ".");
                sb.append((wifiList.get(i)).SSID);
                sb.append("\n");
                wifi_name.add(wifiList.get(i).SSID);
            }





        }

    }


    @Override
    protected void onPause() {

        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    private void showWifiDialog() {
        wifiStartDialog = new WifiStartDialog(HomeActivity.this, R.style.AppTheme, new WifiStartDialog.AlertInterface() {
            @Override
            public void onSubmit(boolean status) {

                slide_second_view.setVisibility(View.GONE);
                slide_third.setVisibility(View.VISIBLE);

                wifiStartDialog.dismiss();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, wifi_name);

                wifi_list.setAdapter(adapter);
            }
        });

        wifiStartDialog.show();
    }

    private void createQrCode() {

    }

    private void generateQrCode(String inputValue) {


        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qr_code_iv.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.e("TAG", e.toString());
            }
        } else {
        }
        slide_third.setVisibility(View.GONE);
        qr_code_view.setVisibility(View.VISIBLE);
        qr_code_iv.setImageBitmap(bitmap);

        checkQrCode();

    }

    private void checkQrCode() {

        local_socket.on("deviceConfig", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                JSONObject data = (JSONObject) args[0];
                Log.e("data", data.toString());

                if (data.has("data")) {
                    try {
                        JSONObject data_prodcut = data.getJSONObject("data");

                        String product_id = data_prodcut.getString("product_id");

                        loginPrefManager.setStringValue("product_id", product_id);

                        goToActivity(HomeActivity.this, DashBoard.class, null);

                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}
