package com.example.cosmotracker;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import pojo.CosmoObject;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        CosmoObject cosmo = (CosmoObject)getIntent().getParcelableExtra("cosmo");



        setCosmoObject(cosmo);
    }
    private void setCosmoObject(CosmoObject cosmo){


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
        /*
        ImageView vis = view.findViewById(R.id.visibility);
        vis.setImageResource(cosmo.setVisibility());

        ImageView type = view.findViewById(R.id.type);
        type.setImageResource(cosmo.setType());
        */
        ImageView frame = findViewById(R.id.selected_frame);
        frame.setImageResource(ImageHelper.getFrame(cosmo.get_type()));


    }
}
