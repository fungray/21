package com.mingrisoft.activity.a21.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.activity.a21.model.Tb_flag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/29.
 */

public class FlagDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public FlagDAO(Context context){
        helper = new DBOpenHelper(context);
    }

    public void add(Tb_flag tb_flag) {
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("insert into tb_flag (_id,flag) values (?,?)",
                new Object[]{ tb_flag.get_id(), tb_flag.getFlag() });
    }

    public void update(Tb_flag tb_flag){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("update tb_flag set flag = ? where _id = ?",
                new Object[]{ tb_flag.get_id(), tb_flag.getFlag() });
    }

    public Tb_flag find(int id){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select _id,flag from tb_flag where _id = ?",
                new String[]{ String.valueOf(id) });
        if(cursor.moveToNext()){
            //将遍历到的收入信息存储到 Tb_flag 类中
            return new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("flag")));
        }
        return null;
    }

    public void delete(Integer... ids){
        if (ids.length > 0){
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<ids.length; i++){
                sb.append('?').append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
            db.execSQL("delete form tb_flag where _id in ("+sb+")",(Object[]) ids);
        }
    }

    public List<Tb_flag> getScrollData(int start, int count){
        List<Tb_flag> tb_inaccounts = new ArrayList<Tb_flag>();
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        //获取所有收入信息
        Cursor cursor = db.rawQuery("select * from tb_flag limit ?,?",
                new String[]{ String.valueOf(start), String.valueOf(count) });
        while(cursor.moveToNext()){//便利所有的收入信息
            //将遍历到的收入信息添加到集合中
            tb_inaccounts.add(new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("flag"))));
        }
        return tb_inaccounts;
    }
    public int getMaxId(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);
        if(cursor.moveToLast()){        //访问Cursor中的最后一条数据
            return cursor.getInt(0);//获取访问到的数据
        }
        return 0;                      //如果没数据，返回0
    }
    public long getCount(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);
        if(cursor.moveToNext()){         //判断Cursor中是否有数据
            return cursor.getLong(0);//返回总记录数
        }
        return 0;                       //如果没数据，返回0
    }

}
