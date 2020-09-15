package com.geekbrains.android.homework.notifications;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.geekbrains.android.homework.R;

public class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = context.getString(R.string.mobile_enabled);
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = context.getString(R.string.wifi_enabled);
                return status;
            }
        } else {
            status = context.getString(R.string.internet_not_enabled);
            return status;
        }
        return status;
    }
}