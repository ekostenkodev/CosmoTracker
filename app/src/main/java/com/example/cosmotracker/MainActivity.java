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

    public List<CosmoObject> CosmoList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvMain);
        CosmoList = getData(this);

        CosmoAdapter adapter= new CosmoAdapter(this, CosmoList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getInfo(CosmoList.get(position)); // TODO возможно нужно исправить, но Intent требует this, что нельзя сделать в лисенере

            }
        });


    }

    public void getInfo(CosmoObject cosmo){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("cosmo", cosmo);
        startActivity(intent);
    }






    public static List<CosmoObject> getData(Context context){

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
