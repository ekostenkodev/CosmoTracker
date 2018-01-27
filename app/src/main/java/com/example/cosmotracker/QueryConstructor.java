package com.example.cosmotracker;

import android.content.Context;
import android.content.SharedPreferences;


public class QueryConstructor {


    private static int CosmoSize = 10;
    public static boolean isChanged = false;

    public QueryConstructor(int CosmoSize){
        this.CosmoSize = CosmoSize;
    }

    public String getQuery(Context context){

        String query = "SELECT * FROM CosmoObjects";

        boolean[][] sortList = SortSharedPreferences.getSavedSortPreferences(context);

        String typeStr = "";
        if(sortList[0][0]) typeStr+="'1'";
        if(sortList[0][1]) typeStr+= (typeStr.length()>0?",":"") + "'2'";
        if(sortList[0][2]) typeStr+= (typeStr.length()>0?",":"") + "'3'";
        if(sortList[0][3]) typeStr+= (typeStr.length()>0?",":"") + "'4'";

        String visStr = "";
        if(sortList[1][0]) visStr+="'1'";
        if(sortList[1][1]) visStr+= (visStr.length()>0?",":"") + "'2'";
        if(sortList[1][2]) visStr+= (visStr.length()>0?",":"") + "'3'";

        query += " WHERE Type IN (" + typeStr + ") AND Visibility IN (" + visStr + ")";

        isChanged = true;

        return query;
    }
}
