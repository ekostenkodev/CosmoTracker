package com.ekostenkodev.cosmotracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.ekostenkodev.cosmotracker.notification.AlarmReceiver;
import com.ekostenkodev.cosmotracker.notification.NotificationHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;

import activities.MainActivity;
import activities.SubsActivity;
import pojo.CosmoObject;


public class Subscription {





    public static boolean isSubscribe(Context context, int id){


        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();
        Cursor cur = null;
        String strSQL = String.format("SELECT * FROM Subscriptions WHERE CosmoID = "+id);

        cur = db.rawQuery(strSQL, null);

        try {
            if (cur.moveToFirst()) {
                db.close();
                cur.close();
                return true;
            }
        } catch (Exception e) {
        }
        db.close();
        cur.close();

        return false;

    }

    public static void addSubscription(Context context, int cosmoID){



        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();



        ContentValues values = new ContentValues();
        values.put("CosmoID", cosmoID);


        db.insert("Subscriptions", null,values);

        if(db != null){
            db.close();
            db = null;
        } oh = null;

        addNotification(context,cosmoID);

    }




    public static void deleteSubscription(Context context, int cosmoID){


        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();

        String selection = "CosmoID" + " LIKE ?";
        String[] selectionArgs = { String.valueOf(cosmoID) };
        db.delete("Subscriptions", selection, selectionArgs);

        if(db != null){
            db.close();
            db = null;
        } oh = null;

        deleteNotification(context,cosmoID);

    }


    private static void addNotification(Context context,int cosmoID)
    {
        CosmoObject cosmoObject = CosmoDataBase.getCosmoObject(context,cosmoID);
        //Date date = new Date();
        NotificationHelper.scheduleRepeatingRTCNotification(context,cosmoObject.get_nextArrival());

/*
        Intent notificationIntent = new Intent(context, SubsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CosmoNotification")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.type_comet)
                .setContentTitle("Последнее космическое предупреждение!")
                .setContentText("До "+cosmoObject.get_name()+" остался один день! Поспеши!") // Текст уведомления
                .setWhen(cosmoObject.get_nextArrival().getTime())
                //.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                //.setAutoCancel(true);
;
        notificationManager.notify(cosmoID, builder.build());

*/

    }

    private static void deleteNotification(Context context,int cosmoID)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(cosmoID);
    }

}
