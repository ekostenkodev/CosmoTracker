package com.ekostenkodev.cosmotracker;

import android.content.Context;


public class QueryConstructor {


    private int cosmoSize;
    public static boolean _isChanged = false;

    public QueryConstructor(int cosmoSize){
        this.cosmoSize = cosmoSize;
    }

    public static boolean isChanged(){

        if(_isChanged == false)
            return false;

        _isChanged = false;
        return true;

    }

    public String getQuery(Context context){


        SortSettings sortSettings = SharedPreferences.getSavedSortPreferences(context);

        String query = "SELECT * FROM CosmoObjects";


        boolean[] type = sortSettings.getValue(SortSettings.sortSwitchers.type);

        String typeStr = "";
        if(type[0]) typeStr+="'1'";
        if(type[1]) typeStr+= (typeStr.length()>0?",":"") + "'2'";
        if(type[2]) typeStr+= (typeStr.length()>0?",":"") + "'3'";
        if(type[3]) typeStr+= (typeStr.length()>0?",":"") + "'4'";

        boolean[] vis = sortSettings.getValue(SortSettings.sortSwitchers.vis);
        String visStr = "";
        if(vis[0]) visStr+="'1'";
        if(vis[1]) visStr+= (visStr.length()>0?",":"") + "'2'";
        if(vis[2]) visStr+= (visStr.length()>0?",":"") + "'3'";

        query += " WHERE Type IN (" + typeStr + ") AND Visibility IN (" + visStr + ")";

        boolean[] order = sortSettings.getValue(SortSettings.sortSwitchers.order);
        if(order[0])
            query += " ORDER BY NextArrival ASC"+ " LIMIT " + cosmoSize;
        else
            query += " ORDER BY NextArrival DESC" + " LIMIT " + cosmoSize;


        return query;
    }
}
