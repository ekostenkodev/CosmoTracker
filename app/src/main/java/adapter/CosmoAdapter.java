package adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmotracker.ImageHelper;
import com.example.cosmotracker.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pojo.CosmoObject;



public class CosmoAdapter extends BaseAdapter {

    private List<CosmoObject> list;
    private LayoutInflater layoutInflater;
    private AssetManager assets;
    public CosmoAdapter(Context context, List<CosmoObject> list) {
        assets = context.getAssets();
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.cosmoobject_layout, viewGroup, false);
        }

        CosmoObject cosmo = getCosmoObject(position);

        TextView name = view.findViewById(R.id.list_name);
        name.setText(cosmo.get_name());


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
        vis.setImageResource(ImageHelper.getVisibility(cosmo.get_type(),cosmo.get_visibility()));

        ImageView frame = view.findViewById(R.id.list_frame);
        frame.setImageResource(ImageHelper.getFrame(cosmo.get_type()));

        ImageView type = view.findViewById(R.id.list_type);
        type.setImageResource(ImageHelper.getType(cosmo.get_type()));



        return view;
    }



    private CosmoObject getCosmoObject(int position){
        return (CosmoObject) getItem(position);
    }


}
