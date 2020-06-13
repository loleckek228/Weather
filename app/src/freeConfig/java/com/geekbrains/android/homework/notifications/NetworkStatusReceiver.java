package com.geekbrains.android.homework.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.geekbrains.android.homework.R;

public class NetworkStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        if (status == null || status.isEmpty()) {
            status = context.getString(R.string.no_internet_connection);
        }

        new Notification(context, R.drawable.ic_disconneting_network,
                status);
    }
}