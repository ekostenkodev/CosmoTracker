package com.example.cosmotracker;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import pojo.CosmoObject;

public class InfoActivity extends AppCompatActivity {

    public int cosmoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String cosmoID = getIntent().getStringExtra("cosmo");
        this.cosmoID = Integer.parseInt(cosmoID);



        setCosmoObject(this.cosmoID);

    }
    private void setCosmoObject(int cosmoID){

        CosmoObject cosmo = CosmoDataBase.getCosmoObject(this, cosmoID);

        TextView name = (TextView) findViewById(R.id.selected_name);
        name.setText(cosmo.get_name());

        TextView info = (TextView) findViewById(R.id.selected_info);

        info.setText(cosmo.get_info());

        ImageView image = findViewById(R.id.selected_image);
        try {
            // get input stream
            InputStream ims = getAssets().open("cosmoimages/"+cosmo.get_image());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex) {
            return ;
        }

        ImageView vis = findViewById(R.id.selected_vis);
        vis.setImageResource(ImageHelper.getVisibility(cosmo.get_type(),cosmo.get_visibility()));

        ImageView type = findViewById(R.id.selected_type);
        type.setImageResource(ImageHelper.getType(cosmo.get_type()));

        ImageView frame = findViewById(R.id.selected_frame);
        frame.setImageResource(ImageHelper.getFrame(cosmo.get_type()));

        ImageView sub = findViewById(R.id.selected_sub);

        if(Subscription.isSubscribe(this,cosmo.get_id()))
            sub.setImageResource(R.drawable.icon_sub_on);
        else
            sub.setImageResource(R.drawable.icon_sub_off);



    }


    public void onSubscribeClick(View view)
    {
        ImageView sub = findViewById(R.id.selected_sub);
        if(Subscription.isSubscribe(this,cosmoID)) {
            sub.setImageResource(R.drawable.icon_sub_off);
            Subscription.deleteSubscribtion(this, cosmoID);
        }
        else {
            sub.setImageResource(R.drawable.icon_sub_on);
            Subscription.addSubscribtion(this, cosmoID);
        }
    }


}
