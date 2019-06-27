package com.app.homeycam.Utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.logging.Logger;

public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {

//            Logger.debug("couldn't get connectivity manager");

        } else {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null) {

                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        return true;
                    }
                }
            }
        }

        return false;

    }

    public static boolean isWifiAvailable(Context mContext) {

        boolean isWifi = false;

        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {

            isWifi = true;
        }

        return isWifi;
    }

    public static boolean isLocationServiceEnabled(Context mContext) {

        LocationManager locationManager = null;

        boolean gps_enabled = false;

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        try {

            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return gps_enabled;

    }
}
