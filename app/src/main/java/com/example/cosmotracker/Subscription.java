package com.example.cosmotracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

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

    public static void addSubscribtion(Context context, int cosmoID){



        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();



        ContentValues values = new ContentValues();
        values.put("CosmoID", cosmoID);


        db.insert("Subscriptions", null,values);

        if(db != null){
            db.close();
            db = null;
        } oh = null;



    }

    public static void deleteSubscribtion(Context context, int cosmoID){


        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();

        String selection = "CosmoID" + " LIKE ?";
        String[] selectionArgs = { String.valueOf(cosmoID) };
        db.delete("Subscriptions", selection, selectionArgs);

        if(db != null){
            db.close();
            db = null;
        } oh = null;



    }

}
