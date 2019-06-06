package com.ekostenkodev.cosmotracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            //NotificationHelper.scheduleRepeatingRTCNotification(context);
            Log.d("B", "BOOT: 1");
            // TODO: 05.06.2019 what 
        }
    }
}
