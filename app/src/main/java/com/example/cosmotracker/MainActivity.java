package com.example.cosmotracker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.CosmoAdapter;
import pojo.CosmoObject;
import pojo.CosmoType;
import pojo.Visibility;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    public static boolean isVisAndTypeFill = false;

    public List<Visibility> visibilityList;
    public List<CosmoType> cosmotypeList;
    public List<CosmoObject> CosmoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadVisibilityAndType(this);


        listView = (ListView) findViewById(R.id.lvMain);
        CosmoList = getData(this,"CosmoObjects");

        CosmoAdapter adapter= new CosmoAdapter(this, CosmoList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getInfo(CosmoList.get(position));
            }
        });






    }

    public void getInfo(CosmoObject cosmo){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("cosmo", cosmo);
        startActivity(intent);
    }



    private void LoadVisibilityAndType(Context context){


        if(!isVisAndTypeFill){
            cosmotypeList = new ArrayList<>();
            visibilityList = new ArrayList<>();

            DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
            SQLiteDatabase db = oh.openDataBase();
            Cursor cur = null;
            String strSQL = String.format("SELECT * FROM %s ","Visibility");
            cur = db.rawQuery(strSQL, null);
            cur.moveToFirst();

            while(!cur.isAfterLast()) {


                visibilityList.add(new Visibility(cur.getInt(0),cur.getString(1)));

                cur.moveToNext();

            }


            strSQL = String.format("SELECT * FROM CosmoType");
            cur = db.rawQuery(strSQL, null);
            cur.moveToFirst();

            while(!cur.isAfterLast()) {

                cosmotypeList.add(new CosmoType(cur.getInt(0),cur.getString(1)));

                cur.moveToNext();
            }

            if(cur != null){
                cur.close();
                cur = null;
            }
            if(db != null){
                db.close();
                db = null;
            } oh = null;

            //
            //
            //

            isVisAndTypeFill = true;

            for(CosmoType c : cosmotypeList)
                Log.d("-----------------",c.get_id()+"  "+c.get_name());

        }

    }


    public void onClick(View view){

        Log.d("-------------", "itemSelect: position =");

    }

    public static List<CosmoObject> getData(Context context, String table){

        ArrayList<CosmoObject> list = new ArrayList<>();

        DatabaseHelper oh = new DatabaseHelper(context, "CosmoTrackerDB.db");
        SQLiteDatabase db = oh.openDataBase();
        Cursor cur = null;
        String strSQL = String.format("SELECT * FROM CosmoObjects");
        cur = db.rawQuery(strSQL, null);
        //cur = oh.getAllData(table);
        cur.moveToFirst();

        while(!cur.isAfterLast()) {


            list.add(new CosmoObject(cur));

            cur.moveToNext();
        }
        if(cur != null){
            cur.close();
            cur = null;
        }
        if(db != null){
            db.close();
            db = null;
        } oh = null;

        return list;
    }
}
