package com.ekostenkodev.cosmotracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            NotificationHelper.scheduleAllRTCNotification(context,intent);
            Log.d("B", "BOOT: 1");
        }
    }
}
