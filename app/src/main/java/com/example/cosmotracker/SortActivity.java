package com.example.cosmotracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;


public class SortActivity extends AppCompatActivity {


    private Switch[][] switchers = new Switch[2][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        setSwitchers();
        SortSharedPreferences.setSavedSwitchers(this,switchers);

    }

    public void setSwitchers(){

        switchers[0] = new Switch[]{
                findViewById(R.id.sort_comets),
                findViewById(R.id.sort_eclipses),
                findViewById(R.id.sort_events),
                findViewById(R.id.sort_planets)};

        switchers[1] = new Switch[]{
                findViewById(R.id.sort_eyes),
                findViewById(R.id.sort_binoculars),
                findViewById(R.id.sort_telescope)};
    }




    public void onSortClick(View view){


        SortSharedPreferences.saveSharedSwitchers(this, switchers);


        finish();
    }
}
