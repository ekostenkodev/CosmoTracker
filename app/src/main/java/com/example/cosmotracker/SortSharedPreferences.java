package com.example.cosmotracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Switch;


public class SortSharedPreferences {

    public static boolean[][] getSavedSortPreferences(Context context){

        boolean[][] sortList = {new boolean[4], new boolean[3]};
        sortList[0] = new boolean[4];
        sortList[1] = new boolean[3];

        SharedPreferences sortSettings = context.getSharedPreferences("sortSettings", context.MODE_PRIVATE);

        sortList[0][0] = sortSettings.getBoolean("comets", true);
        sortList[0][1] = sortSettings.getBoolean("eclipses", true);
        sortList[0][2] = sortSettings.getBoolean("events", true);
        sortList[0][3] = sortSettings.getBoolean("planets", true);

        sortList[1][0] = sortSettings.getBoolean("eyes", true);
        sortList[1][1] = sortSettings.getBoolean("binoculars", true);
        sortList[1][2] = sortSettings.getBoolean("telescope", true);

        return sortList;
    }



    public static void saveSharedSwitchers(Context context, Switch[][] switchers){

        SharedPreferences sortSettings =context.getSharedPreferences("sortSettings", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sortSettings.edit();

        editor.putBoolean("comets",switchers[0][0].isChecked());
        editor.putBoolean("eclipses",switchers[0][1].isChecked());
        editor.putBoolean("events",switchers[0][2].isChecked());
        editor.putBoolean("planets",switchers[0][3].isChecked());

        editor.putBoolean("eyes",switchers[1][0].isChecked());
        editor.putBoolean("binoculars",switchers[1][1].isChecked());
        editor.putBoolean("telescope",switchers[1][2].isChecked());

        editor.commit();

    }

    public static void setSavedSwitchers(Context context, Switch[][] switchers){


        boolean[][] sortList = SortSharedPreferences.getSavedSortPreferences(context);

        for (int i = 0; i < switchers.length; i++)
            for (int j = 0; j < switchers[i].length; j++)
                switchers[i][j].setChecked(sortList[i][j]);
        /*

        switchers[0][0].setChecked(sortList[0][0]);
        switchers[0][1].setChecked(sortList[0][1]);
        switchers[0][2].setChecked(sortList[0][2]);
        switchers[0][3].setChecked(sortList[0][3]);

        switchers[1][0].setChecked(sortList[1][0]);
        switchers[1][1].setChecked(sortList[1][1]);
        switchers[1][2].setChecked(sortList[1][2]);

        */

    }
}
