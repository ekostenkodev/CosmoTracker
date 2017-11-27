package pojo;

import android.database.Cursor;

import com.example.cosmotracker.R;

import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class CosmoObject implements Parcelable {

    private int _id;
    private String _name;
    private int _type;
    private Date _nextArrival;
    private Date _period;
    private String _location;
    private String _info;
    private int _visibility;
    private String _image;



    public  CosmoObject(Cursor cursor){

        this._id = cursor.getInt(0);


        this._name = cursor.getString(1);
        this._type = cursor.getInt(2);
        //this._nextArrival = Date.valueOf(cursor.getString(3));
        //this._period = Date.valueOf(cursor.getString(4));
        //this._location = Integer.parseInt(cursor.getString(5));
        this._info = cursor.getString(6);
        this._visibility = cursor.getInt(7);

        this._image = "img" + this._id+".png";



    }


    public CosmoObject(Parcel in) {
        String[] data = new String[9];
        in.readStringArray(data);

        this._id = Integer.parseInt(data[0]);
        this._name = data[1];
        this._type = Integer.parseInt(data[2]);
        //this._nextArrival = Date.valueOf(data[3]);
        //this._period = Date.valueOf(data[4]);
        //this._location = data[5];
        this._info = data[6];
        this._visibility = Integer.parseInt(data[7]);
        this._image = data[8];
    }

    public static final Parcelable.Creator<CosmoObject> CREATOR = new Parcelable.Creator<CosmoObject>() {

        @Override
        public CosmoObject createFromParcel(Parcel source) {
            return new CosmoObject(source);
        }

        @Override
        public CosmoObject[] newArray(int size) {
            return new CosmoObject[size];
        }
    };

    public int setVisibility(){

        switch (_type){
            case 1: // event
                switch (_visibility){
                    case 2: // tele
                            return R.drawable.telescope_event;

                    case 3: // eye
                            return R.drawable.eye_event;

                }
                break;
            case 2: // eclipse
                switch (_visibility){
                    case 2: // tele
                        return R.drawable.telescope_eclipse;

                    case 3: // eye
                        return R.drawable.eye_eclipse;

                }
                break;

            case 3: // planet
                break;
            case 4: // comet
                switch (_visibility){
                    case 2: // tele
                        return R.drawable.telescope_comet;

                    case 3: // eye
                        return R.drawable.eye_comet;

                }
                break;

        }



        return 0;
    } // TODO: ПЕРЕДЕЛАТЬ!!!!!
    public int setType(){
        switch (_type){
            case 1:
                return R.drawable.icon_event;
            case 2:
                return R.drawable.icon_eclipse;
            case 3:

            case 4:
                return R.drawable.icon_comet;
        }
    return 0;
    }// TODO: ПЕРЕДЕЛАТЬ!!!!!
    public int setFrame(){
        switch (_type){
            case 1:
                return R.drawable.frame_event;
            case 2:
                return R.drawable.frame_eclipse;
            case 3:

            case 4:
                return R.drawable.frame_comet;
        }
        return 0;
    }// TODO: ПЕРЕДЕЛАТЬ!!!!!

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public int get_type() {
        return _type;
    }

    public Date get_nextArrival() {
        return _nextArrival;
    }

    public Date get_period() {
        return _period;
    }

    public String get_location() {
        return _location;
    }

    public String get_info() {
        return _info;
    }

    public int get_visibility() {
        return _visibility;
    }

    public String get_image() {
        return _image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                String.valueOf(_id),
                _name, String.valueOf(_type),
                String.valueOf(_nextArrival),
                String.valueOf(_period),
                _location,_info,
                String.valueOf(_visibility),_image});
    }
}
