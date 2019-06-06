package com.ekostenkodev.cosmotracker.notification;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.ekostenkodev.cosmotracker.R;

import java.sql.Date;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;


import activities.MainActivity;

import static android.content.Context.ALARM_SERVICE;



public class NotificationHelper {
    public static int ALARM_TYPE_RTC = 100;
    private static Dictionary<Integer,AlarmManager> alarmManagerRTC;
    private static PendingIntent alarmIntentRTC;
    private Context _context;
    public NotificationHelper(Context context)
    {
        _context = context;
        Intent intent = new Intent(context, AlarmReceiver.class);

        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }




    public static void scheduleAllRTCNotification(Context context,Intent intent)
    {

    }
    public static void scheduleRTCNotification(Context context, int cosmoId,Date date) {

        //получаем экземпляр calendar для получения возможности задать время срабатывания сигнала
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //Настраиваем время (здесь 8 утра) отправки ежедневного уведомления
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,20);
        calendar.set(Calendar.MINUTE,0);


        //получаем экземпляр службы AlarmManager
        AlarmManager newAlarm = (AlarmManager)context.getSystemService(ALARM_SERVICE);

        //Настраиваем сигнал для пробуждения устройства каждый день в заданное время.
        //AlarmManager.RTC_WAKEUP отвечает за пробуждение устройства, поэтому нужно аккуратно его использовать.
        //Используйте RTC когда вам нет необходимости пробуждать устройство, и уведомления будут приходить только когда оно не спит
        //Мы используем здесь RTC.WAKEUP только для демонстрации
        newAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentRTC);

        alarmManagerRTC.put(cosmoId,newAlarm);
    }



    public static void cancelAlarmRTC(int cosmoID) {
        if (alarmManagerRTC!= null) {
            alarmManagerRTC.get(cosmoID).cancel(alarmIntentRTC);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {

        NotificationManager notificationManager =  (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("CosmoNotification", "CosmoNotification", NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("CosmoTracker");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }
        return notificationManager;
        //return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Enable boot receiver to persist alarms set for notifications across device reboots
     */
    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }


}
