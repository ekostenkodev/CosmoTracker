package pojo;

/**
 * Created by Егор on 27.11.2017.
 */

public class Visibility {

    private int _id;
    private String _name;

    public Visibility(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public int get_id() {

        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
