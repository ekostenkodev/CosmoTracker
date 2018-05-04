package com.ekostenkodev.cosmotracker;

public class ImageHelper {

    private static int[] _type;
    private static int[] _frame;
    private static int[] _visibility;

    static {
        _type = new int[4];
        _frame = new int[4];
        _visibility = new int[3];
        //
        //
        //
        _visibility[0] = R.drawable.vis_eye;
        _visibility[1] = R.drawable.vis_bino;
        _visibility[2] = R.drawable.vis_telescope;
        //
        //
        //
        _type[0] = R.drawable.type_comet;
        _type[1] = R.drawable.type_eclipse;
        _type[2] = R.drawable.type_event;
        _type[3] = R.drawable.type_planet;
        //
        //
        //
        _frame[0] = R.drawable.frame_comet;
        _frame[1] = R.drawable.frame_eclipse;
        _frame[2] = R.drawable.frame_event;
        _frame[3] = R.drawable.frame_planet;


    }

    public static int getType(int type){
        return _type[type - 1];
    }

    public static int getFrame(int type){
        return _frame[type - 1];
    }

    public static int getVisibility(int visibility){
        return _visibility[visibility - 1];
    }



}
