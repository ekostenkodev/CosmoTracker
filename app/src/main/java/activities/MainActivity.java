package activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.cosmotracker.CosmoDataBase;
import com.example.cosmotracker.CosmoDelegate;
import com.example.cosmotracker.QueryConstructor;
import com.example.cosmotracker.R;
import com.example.cosmotracker.Subscription;

import java.util.ArrayList;

import adapter.CosmoAdapter;
import pojo.CosmoObject;

public class MainActivity extends AppCompatActivity {


    private final int MIN_SIZE = 2;

    private CosmoAdapter adapter;
    private int CosmoSize = MIN_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo пустить загрузку

        setCosmoAdapter(getCosmoList(MIN_SIZE));

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

    public void onSortMenuClick(View view){
        Intent intent = new Intent(this, SortActivity.class);
        startActivity(intent);
    }

    public void onMenuClick(View view){
        adapter.notifyDataSetChanged();
    }









}
