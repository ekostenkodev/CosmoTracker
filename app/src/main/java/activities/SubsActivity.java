package activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.ekostenkodev.cosmotracker.QueryConstructor;
import com.ekostenkodev.cosmotracker.R;

import java.util.ArrayList;

import adapter.CosmoAdapter;
import pojo.CosmoObject;

public class SubsActivity extends AppCompatActivity {

    private CosmoAdapter adapter;
    private ArrayList<CosmoObject> cosmoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subs);


        ListView listView =  (ListView) findViewById(R.id.lvMain);

        setToolbar();

        QueryConstructor queryConstructor = new QueryConstructor(this , QueryConstructor.queryType.subs );
        adapter = new CosmoAdapter(this, listView, queryConstructor);
        adapter.fillList(CosmoAdapter.MIN_SIZE);

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
}
