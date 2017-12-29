package com.mingrisoft.activity.a21.model;

/**
 * Created by apple on 2017/12/29.
 */

public class Tb_flag {
    private int _id;     //便签编号
    private String flag; //便签类型

    public Tb_flag(int id,String flag){
        super();
        this._id = id;
        this.flag = flag;
    }

    public int get_id() {
        return _id;
    }

    public String getFlag() {
        return flag;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
