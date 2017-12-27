package com.mingrisoft.activity.a21.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.activity.a21.model.Tb_pwd;

/**
 * Created by apple on 2017/12/27.
 */

public class PwdDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public PwdDAO(Context context){
        helper = new DBOpenHelper(context);
    }

    public Tb_pwd find(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select password from tb_pwd",null);
        if(cursor.moveToNext()){
            //将遍历到的收入信息存储到 Tb_pwd 类中
            return new Tb_pwd(cursor.getString(cursor.getColumnIndex("password")));
        }
        return null;
    }

    public long getCount(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(password) from tb_pwd", null);
        if(cursor.moveToNext()){         //判断Cursor中是否有数据
            return cursor.getLong(0);//返回总记录数
        }
        return 0;                       //如果没数据，返回0
    }

    public String getPassword(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象


        return null;
    }
}
