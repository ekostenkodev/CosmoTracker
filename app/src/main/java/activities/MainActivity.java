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
import android.widget.ListView;
import android.widget.Toast;

import com.ekostenkodev.cosmotracker.CosmoDataBase;
import com.ekostenkodev.cosmotracker.QueryConstructor;
import com.ekostenkodev.cosmotracker.R;

import java.util.ArrayList;
import adapter.CosmoAdapter;

import pojo.CosmoObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    private CosmoAdapter adapter;
    private ArrayList<CosmoObject> cosmoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView =  (ListView) findViewById(R.id.lvMain);

        setToolbar();

        QueryConstructor queryConstructor = new QueryConstructor(this , QueryConstructor.queryType.all );
        adapter = new CosmoAdapter(this, listView, queryConstructor);
        listView.setAdapter(adapter);

       // Log.d("-------Size--------", String.valueOf(CosmoDataBase.getSize(this, QueryConstructor.queryType.all)));

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_subs:
                intent = new Intent(this, SubsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:

                Toast toast = Toast.makeText(this,
                        "Не сегодня!", Toast.LENGTH_SHORT);
                toast.show();

                break;

            case R.id.nav_autor:
                intent = new Intent(this, AutorActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.refresh();
    }

    public void onSortMenuClick(){
        Intent intent = new Intent(this, SortActivity.class);
        startActivity(intent);
    }










}
