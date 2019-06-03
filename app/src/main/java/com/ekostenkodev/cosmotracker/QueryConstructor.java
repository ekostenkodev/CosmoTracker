package com.ekostenkodev.cosmotracker;

import android.content.Context;

import java.lang.reflect.Type;
import java.sql.Date;


public class QueryConstructor {


    public enum queryType{all, subs}

    public static boolean _isChanged = false;

    private queryType _type;
    private Context _context;

    public QueryConstructor(Context context, queryType type ){
        this._context = context;
        this._type = type;
    }

    public static boolean isChanged(){

        if(_isChanged == false)
            return false;

        _isChanged = false;
        return true;

    }

    public String getQuery(Date lastDate,int size){

        switch (_type){
            case all:
                    return getQueryForAll(lastDate,size);
            case subs:
                    return getQueryForSubs(lastDate,size);
        }

        return null;
    }

    public String getQueryForAll(Date lastDate, int size){


        SortSettings sortSettings = SharedPreferences.getSavedSortPreferences(_context);

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
        {
            if(lastDate==null)
                lastDate = new Date(0);
            query += " AND NextArrival>'"+lastDate+"' ORDER BY NextArrival ASC";

        }
        else
        {
            if (lastDate == null)
                lastDate = new Date(3000,1,1);
            query += " AND NextArrival<'" + lastDate + "'ORDER BY NextArrival DESC";
        }

        query += " LIMIT " + size;


        return query;
    }

    public String getQueryForSubs(Date lastDate, int size){

        SortSettings sortSettings = SharedPreferences.getSavedSortPreferences(_context);

        String query = "SELECT * FROM CosmoObjects WHERE _id IN (SELECT CosmoID FROM Subscriptions)"; // todo запрос

        query += " LIMIT " + size;

        return query;
    }

    public queryType getType(){ return _type; };
}
