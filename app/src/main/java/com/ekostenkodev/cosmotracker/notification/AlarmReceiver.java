package com.ekostenkodev.cosmotracker.notification;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ekostenkodev.cosmotracker.R;

import activities.MainActivity;


/**
 * AlarmReceiver handles the broadcast message and generates Notification
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Intent для вызова приложения по клику.
        //Мы хотим запустить наше приложение (главную активность) при клике на уведомлении
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        //настроим флаг для перезапуска приложения
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        //Создаём уведомление
        Notification repeatedNotification = buildLocalNotification(context, pendingIntent).build();
        Log.d("B", "OnRec: "+System.currentTimeMillis());

        //Отправляем уведомление
        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);

    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent) {
        Log.d("a", "buildLocalNotification: 1");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CosmoNotification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.type_comet)
                .setContentTitle("Последнее космическое предупреждение!")
                .setContentText("До события остался один день! Поспеши!")// Текст уведомления
                .setAutoCancel(true);
        //.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
        //.setAutoCancel(true);
        Log.d("a", "buildLocalNotification: 2");

        return builder;
    }
}
