package pojo;

import android.database.Cursor;

import com.example.cosmotracker.R;

import java.sql.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class CosmoObject {

    private int _id; // id космического объекта (из таблицы CosmoObjects)
    private String _name; // имя космического объекта
    private int _type; //тип космического объекта (индекс в int из таблицы CosmoType)
    private Date _nextArrival; // следующий перигелий
    private Date _period; // период обращения
    private String _location; // локация, где можно будет наблюдать за космическим объектом
    private String _info; // информация о космическом объекте
    private int _visibility; // видимость объекта (индекс в int из таблицы Visibility)
    private String _image; // имя изображения



    public  CosmoObject(Cursor cursor){
        /*
        Констрункор космического объекта из sql запроса (cursor)
         */


        this._id = cursor.getInt(0);


        this._name = cursor.getString(1);
        this._type = cursor.getInt(2);
        this._nextArrival = Date.valueOf(cursor.getString(3)); // TODO настройка даты
        //this._period = Date.valueOf(cursor.getString(4));
        //this._location = Integer.parseInt(cursor.getString(5));
        this._info = cursor.getString(6);
        this._visibility = cursor.getInt(7);

        this._image = "img" + this._id+".png"; // TODO правильный доступ к ресурсам



    }





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


}
