package activities;

import android.content.Intent;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ekostenkodev.cosmotracker.CosmoDataBase;
import com.ekostenkodev.cosmotracker.CosmoDelegate;
import com.ekostenkodev.cosmotracker.QueryConstructor;
import com.ekostenkodev.cosmotracker.R;
import com.ekostenkodev.cosmotracker.Subscription;
import java.util.ArrayList;
import adapter.CosmoAdapter;

import pojo.CosmoObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private final int MIN_SIZE = 2;

    private CosmoAdapter adapter;
    private int CosmoSize = MIN_SIZE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo пустить загрузку

        setToolbar();


        setCosmoAdapter(getCosmoList(MIN_SIZE));




    }

    public void setToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_button, menu);
        //menu.findItem(R.menu.sort_button).setOnMenuItemClickListener(e -> onSortMenuClick());

        menu.findItem(R.id.nav_sort).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onSortMenuClick();

                return true;
            }
        });


        return true;
    }


    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("-------","xxx" + item.getItemId());

        switch (id){
            case R.id.nav_subs:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_autor:

                Intent intent = new Intent(this, AutorActivity.class);
                startActivity(intent);

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }



    public ArrayList<CosmoObject> getCosmoList(int cosmoSize){

        QueryConstructor query = new QueryConstructor(cosmoSize);

        ArrayList<CosmoObject> CosmoList = CosmoDataBase.getData(this, query.getQuery(this));

        return CosmoList;
    }


    private void setCosmoAdapter(ArrayList<CosmoObject> CosmoList){

        ListView listView = (ListView) findViewById(R.id.lvMain);

        CosmoDelegate subBtn = new CosmoDelegate() {
            @Override
            public void onBtnClick(int cosmoID) {
                listSubscriptionCLick(cosmoID);
                adapter.notifyDataSetChanged();
            }
        };

        CosmoDelegate viewBtn = new CosmoDelegate() {
            @Override
            public void onBtnClick(int cosmoID) {
                listCosmoInfoClick(cosmoID);
            }
        };


        adapter = new CosmoAdapter(this, CosmoList, subBtn, viewBtn);

        listView.setAdapter(adapter);



        ImageButton but = new ImageButton(this);
        but.setImageResource(R.drawable.button_down);
        but.setBackgroundResource(R.color.back_dark);
        but.setScaleType(ImageView.ScaleType.FIT_CENTER);

        but.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));// todo сделать кнопке padding

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CosmoSize += MIN_SIZE;

                ArrayList<CosmoObject> newList = getCosmoList(CosmoSize);
                // todo пустить загрузку

                if(newList.size() == adapter.getCount()) {
                    ImageButton imageButton = (ImageButton)v;
                    imageButton.setVisibility(View.INVISIBLE);// todo кнопка продолжает занимать место, исправить

                    return;
                }


                adapter.setNewList(getCosmoList(CosmoSize));
                adapter.notifyDataSetChanged();




            }
        });


       listView.addFooterView(but);

    }

    public void listSubscriptionCLick(int cosmoID){

        if(Subscription.isSubscribe(this,cosmoID))
            Subscription.deleteSubscribtion(this, cosmoID);
        else
            Subscription.addSubscribtion(this, cosmoID);


    }

    public void listCosmoInfoClick(int cosmoID){

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("cosmo", cosmoID);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();

        if(QueryConstructor.isChanged()) { // todo изменить механизм обновления списка
            CosmoSize = MIN_SIZE;
            adapter.setNewList(getCosmoList(CosmoSize));
        }
        adapter.notifyDataSetChanged();
    }

    public void onSortMenuClick(){
        Intent intent = new Intent(this, SortActivity.class);
        startActivity(intent);
    }










}
