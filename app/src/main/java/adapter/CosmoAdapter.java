package adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmotracker.CosmoDelegate;
import com.example.cosmotracker.ImageHelper;
import com.example.cosmotracker.R;
import com.example.cosmotracker.Subscription;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pojo.CosmoObject;



public class CosmoAdapter extends BaseAdapter {

    private ArrayList<CosmoObject> list;
    private LayoutInflater layoutInflater;
    private AssetManager assets;
    private Context context;
    private CosmoDelegate subDelegate, viewDelegate;


    public CosmoAdapter(Context context, ArrayList<CosmoObject> list, CosmoDelegate subDelegate, CosmoDelegate viewDelegate) {
        assets = context.getAssets();
        this.list = list;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.subDelegate = subDelegate;
        this.viewDelegate = viewDelegate;

    }

    public List<CosmoObject>  getList(){
        return list;
    }

    public void setNewList(ArrayList<CosmoObject> newList){
        list.clear();
        list.addAll(newList);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.cosmoobject_layout, viewGroup, false);
        }



        CosmoObject cosmo = (CosmoObject) getItem(position);

        final int cosmoID = cosmo.get_id();


        TextView name = view.findViewById(R.id.list_name);
        name.setText(cosmo.get_name()); // todo длинное имя западает на след строку, исправить


        TextView info = view.findViewById(R.id.list_info);
        info.setText(cosmo.get_info());

        ImageView image = view.findViewById(R.id.list_image);
        try {
            // get input stream
            InputStream ims = assets.open("cosmoimages/"+cosmo.get_image());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex) {
            return null;
        }




        ImageView vis = view.findViewById(R.id.list_visibility);
        vis.setImageResource(ImageHelper.getVisibility(cosmo.get_visibility()));

        ImageView frame = view.findViewById(R.id.list_frame);
        frame.setImageResource(ImageHelper.getFrame(cosmo.get_type()));

        ImageView type = view.findViewById(R.id.list_type);
        type.setImageResource(ImageHelper.getType(cosmo.get_type()));

        ImageButton sub = view.findViewById(R.id.list_sub);

        if(Subscription.isSubscribe(context,cosmoID))
            sub.setImageResource(R.drawable.icon_sub_on);
        else
            sub.setImageResource(R.drawable.icon_sub_off);


        sub.setTag(position);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subDelegate.onBtnClick(cosmoID);
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDelegate.onBtnClick(cosmoID);
            }
        });

        TextView timer = view.findViewById(R.id.list_timer);

        Date nextArrival = cosmo.get_nextArrival();
        Date current = new Date(System.currentTimeMillis());

        long days = (nextArrival.getTime() - current.getTime())/(1000 * 60 * 60 * 24);

        timer.setText(getCorrectDay(days, "дней","день","дня"));

        return view;
    }


    public String getCorrectDay(long days,String str1,String str2,String str3) {

        long value = days % 100;

        if (value > 10 && value < 20) return days + " " + str1;
        else {
            value = days % 10;

            if (value == 1) return days + " " + str2;
            else if (value > 1 && value < 5) return days + " " + str3;
            else return days + " " + str1;
        }

    }





}

