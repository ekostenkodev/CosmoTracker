package com.ekostenkodev.cosmotracker;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;


public class SharedPreferences {
    static String[] Types = {"comets", "eclipses", "events", "planets"};
    static String[]  Vis = {"eyes", "binoculars", "telescope"};
    static String[] Order = {"order"};

    public static SortSettings getSavedSortPreferences(Context context){

        android.content.SharedPreferences sortSettings = context.getSharedPreferences("sortSettings", context.MODE_PRIVATE);

        boolean[] type = new boolean[Types.length];
        boolean[] vis = new boolean[Vis.length];
        boolean[] order = new boolean[Order.length];

        for (int i = 0; i < Types.length; i++)
            type[i] = sortSettings.getBoolean(Types[i], true);

        for (int i = 0; i < Vis.length; i++)
            vis[i] = sortSettings.getBoolean(Vis[i], true);

        for (int i = 0; i < Order.length; i++)
            order[i] = sortSettings.getBoolean(Order[i], true);

        return new SortSettings(type,vis,order);
    }



    public static void saveSavedSortPreferences(Context context, SortSettings sortSettings){

        android.content.SharedPreferences savedDortSettings =context.getSharedPreferences("sortSettings", context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = savedDortSettings.edit();

        boolean[] typeSettings = sortSettings.getValue(SortSettings.sortSwitchers.type);
        for (int i = 0; i < typeSettings.length; i++)
            editor.putBoolean(Types[i], typeSettings[i]);

        boolean[] visSettings = sortSettings.getValue(SortSettings.sortSwitchers.vis);
        for (int i = 0; i < visSettings.length; i++)
            editor.putBoolean(Vis[i], visSettings[i]);

        boolean[] orderSettings = sortSettings.getValue(SortSettings.sortSwitchers.order);
        for (int i = 0; i < orderSettings.length; i++)
            editor.putBoolean(Order[i], orderSettings[i]);

        editor.commit();

    }




}
