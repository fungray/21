package com.mingrisoft.activity.a21.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 2017/12/26.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;              //定义数据库版本号
    private static final String DBNAME = "account.db";//定义数据库名
    public DBOpenHelper(Context context){                  //定义构造函数
        super(context, DBNAME, null, VERSION); //重写基类的构造函数
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table tb_outaccount (_id integer primary key,money decimal,time varchat(10)," +
                "type varchar(10),address varchar(100),mark varchar(200))");                          //创建支出信息表
        sqLiteDatabase.execSQL("create table tb_inaccount (_id integer primary key,money decimal,tme varchar(10)," +
                "type varchar(10),handler varchar(100),mark varchar(200))");                          //创建收入信息表
        sqLiteDatabase.execSQL("create table tb_pwd (password varchar(20))");                       //创建密码表
        sqLiteDatabase.execSQL("create table tb_falg (_id integer primary key,flag varchar(200))");//创建便签信息表
    }

    //覆写基类的onUpgrade方法，以便数据库版本更新
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
