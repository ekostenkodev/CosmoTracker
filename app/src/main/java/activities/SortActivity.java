package activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;

import com.ekostenkodev.cosmotracker.QueryConstructor;
import com.ekostenkodev.cosmotracker.R;
import com.ekostenkodev.cosmotracker.SharedPreferences;
import com.ekostenkodev.cosmotracker.SortSettings;


public class SortActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);


        setToolbar();


        setSavedSortSettings();

    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true; // onOptionsItemSelected(item)
    }


    public void setSavedSortSettings(){


        SortSettings sortList = SharedPreferences.getSavedSortPreferences(this);

        SwitchCompat[] typeSwitch = new SwitchCompat[]{
                findViewById(R.id.sort_comets),
                findViewById(R.id.sort_eclipses),
                findViewById(R.id.sort_events),
                findViewById(R.id.sort_planets)};

        SwitchCompat[] visSwitch = new SwitchCompat[]{
                findViewById(R.id.sort_eyes),
                findViewById(R.id.sort_binoculars),
                findViewById(R.id.sort_telescope)};

        Spinner[] orderSpinner = new Spinner[] {findViewById(R.id.sort_order)};

        boolean[] savedType = sortList.getValue(SortSettings.sortSwitchers.type);
        for (int i = 0; i < typeSwitch.length; i++)
            typeSwitch[i].setChecked(savedType[i]);

        boolean[] savedVis = sortList.getValue(SortSettings.sortSwitchers.vis);
        for (int i = 0; i < savedVis.length; i++)
            visSwitch[i].setChecked(savedVis[i]);

        boolean[] savedOrder = sortList.getValue(SortSettings.sortSwitchers.order);
        for (int i = 0; i < savedOrder.length; i++)
            orderSpinner[i].setSelection(savedOrder[i]?0:1); // отпратительно




    }


    public void onSortClick(View view){

        View rootView = ((Activity)this).getWindow().getDecorView().findViewById(android.R.id.content);

        SortSettings sortSettings = new SortSettings(rootView);

        SharedPreferences.saveSavedSortPreferences(this, sortSettings);

        QueryConstructor._isChanged = true;

        finish();
    }
}
