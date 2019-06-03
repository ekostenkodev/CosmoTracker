package com.ekostenkodev.cosmotracker;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;

public class SortSettings {

    public enum sortSwitchers{ type, vis , order};

    private boolean[] type = new boolean[4];
    private boolean[] vis = new boolean[3];
    private boolean[] order = new boolean[1];

    public SortSettings(boolean[] type, boolean[] vis, boolean[] order){
        this.type = type;
        this.vis = vis;
        this.order = order;
    }

    public SortSettings(View view){
        SwitchCompat[] typeSwitch = new SwitchCompat[]{
                view.findViewById(R.id.sort_comets),
                view.findViewById(R.id.sort_eclipses),
                view.findViewById(R.id.sort_events),
                view.findViewById(R.id.sort_planets)};

        SwitchCompat[] visSwitch = new SwitchCompat[]{
                view.findViewById(R.id.sort_eyes),
                view.findViewById(R.id.sort_binoculars),
                view.findViewById(R.id.sort_telescope)};

        for (int i = 0; i < typeSwitch.length; i++)
            type[i] = typeSwitch[i].isChecked();

        for (int i = 0; i < visSwitch.length; i++)
            vis[i] = visSwitch[i].isChecked();

        order[0] = ((Spinner)view.findViewById(R.id.sort_order)).getSelectedItemPosition()==0?true:false;
    }

    public boolean[] getValue(sortSwitchers sortType){

        switch (sortType) {
            case type:
                return type;
            case vis:
                return vis;
            case order:
                return order;
        }

        return null;
    }

}
