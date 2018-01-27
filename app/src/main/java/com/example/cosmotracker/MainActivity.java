package com.example.cosmotracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import pojo.CosmoObject;

public class MainActivity extends AppCompatActivity {


    private List<CosmoObject> CosmoList;
    private CosmoAdapter adapter;

    private int CosmoSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo пустить загрузку

        QueryConstructor query = new QueryConstructor(10);

        CosmoList = CosmoDataBase.getData(this, query.getQuery(this), CosmoSize);
        setCosmoAdapter();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setCosmoAdapter(){

        ListView listView = (ListView) findViewById(R.id.lvMain);

        CosmoDelegate subBtn = new CosmoDelegate() {
            @Override
            public void onBtnClick(int position) {
                listSubscriptionCLick(CosmoList.get(position).get_id());
                adapter.notifyDataSetChanged();
            }
        };

        CosmoDelegate viewBtn = new CosmoDelegate() {
            @Override
            public void onBtnClick(int position) {
                listCosmoInfoClick(CosmoList.get(position).get_id());
            }
        };


        adapter= new CosmoAdapter(this, CosmoList, subBtn, viewBtn);

        listView.setAdapter(adapter);

        ImageView but = new ImageView(this);
        but.setImageResource(R.drawable.comet_eye);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CosmoSize += 10;
                CosmoList.clear();
                // todo CosmoList.addAll(CosmoDataBase.getData(getBaseContext(), SortActivity.getQuery(), CosmoSize));
            }
        });

    }

    public void listSubscriptionCLick(int cosmoID){

        if(Subscription.isSubscribe(this,cosmoID)) {
            Subscription.deleteSubscribtion(this, cosmoID);
        }
        else {
            Subscription.addSubscribtion(this, cosmoID);
        }
    }

    public void listCosmoInfoClick(int cosmoID){

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("cosmo", String.valueOf(cosmoID));
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();

        //Log.d("-------",SortActivity.getQuery());


        CosmoSize = 10;
        CosmoList.clear();
        CosmoList.addAll(CosmoDataBase.getData(this, new QueryConstructor(10).getQuery(this), CosmoSize));


        //Log.d("00000000000", "  " + CosmoList.size());

        adapter.notifyDataSetChanged();

    }

    public void onSortMenuClick(View view){
        Intent intent = new Intent(this, SortActivity.class);
        startActivity(intent);
    }

    public void onMenuClick(View view){

        adapter.notifyDataSetChanged();
    }









}
