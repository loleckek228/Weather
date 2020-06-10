package com.geekbrains.android.homework.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.geekbrains.android.homework.R;

public class BatteryStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new Notification(context, R.drawable.ic_battery_low,"Уровень заряда низкий");
    }
}