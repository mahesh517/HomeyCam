package com.app.homeycam.FCM;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.app.homeycam.Activities.HomeActivity;
import com.app.homeycam.R;
import com.app.homeycam.Utils.LoginPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;


@SuppressLint("Registered")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    LoginPrefManager loginPrefManager;


    NotificationManager notificationManager;

    NotificationCompat.BigTextStyle bigText;

    @Override
    public void onCreate() {
        super.onCreate();
        loginPrefManager = new LoginPrefManager(getBaseContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        bigText = new NotificationCompat.BigTextStyle();
        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
//            Log.e("JSON_OBJECT", object.toString());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }


        String notificationTitle = remoteMessage.getData().get("messagebody");

        showNotification(notificationTitle);

    }

    private void showNotification(String messageBody) {

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = getString(R.string.default_notification_channel_id);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setColor(getResources().getColor(R.color.transparent));
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setPriority(Notification.PRIORITY_DEFAULT);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(new Intent("updated").putExtra("start_order", "1"));

    }


}

