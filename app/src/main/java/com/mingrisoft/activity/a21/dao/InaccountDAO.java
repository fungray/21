package com.mingrisoft.activity.a21.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.activity.a21.model.Tb_inaccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/26.
 */

public class InaccountDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public InaccountDAO(Context context){
        helper = new DBOpenHelper(context);
    }

    public void add(Tb_inaccount tb_inaccount) {
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("insert into tb_inaccount (_id,money,time,type,handler,mark) values (?,?,?,?,?,?)",
                new Object[]{ tb_inaccount.get_id(), tb_inaccount.getMoney(), tb_inaccount.getTime(), tb_inaccount.getType(),
                        tb_inaccount.getHandler(), tb_inaccount.getMark() });
    }

    public void update(Tb_inaccount tb_inaccount){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("update tb_inaccount set money = ?,time = ?,type = ?,handler = ?,mark = ? where _id = ?",
                new Object[]{ tb_inaccount.getMoney(), tb_inaccount.getTime(), tb_inaccount.getType(),
                        tb_inaccount.getHandler(), tb_inaccount.getMark(), tb_inaccount.get_id() });
    }

    public Tb_inaccount find(int id){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select _id,money,time,type,handler,mark from tb_inaccount where _id = ?",
                new String[]{ String.valueOf(id) });
        if(cursor.moveToNext()){
            //将遍历到的收入信息存储到 Tb_inaccount 类中
            return new Tb_inaccount(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("handler")),
                    cursor.getString(cursor.getColumnIndex("mark")));
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
            db.execSQL("delete from tb_inaccount where _id in ("+sb+")",(Object[]) ids);
        }
    }

    public List<Tb_inaccount> getScrollData(int start,int count){
        List<Tb_inaccount> tb_inaccounts = new ArrayList<Tb_inaccount>();
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        //获取所有收入信息
        Cursor cursor = db.rawQuery("select * from tb_inaccount limit ?,?",
                new String[]{ String.valueOf(start), String.valueOf(count) });
        while(cursor.moveToNext()){//便利所有的收入信息
            //将遍历到的收入信息添加到集合中
            tb_inaccounts.add(new Tb_inaccount(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("handler")),
                    cursor.getString(cursor.getColumnIndex("mark"))));
        }
        return tb_inaccounts;
    }

    public long getCount(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(_id) from tb_inaccount", null);
        if(cursor.moveToNext()){         //判断Cursor中是否有数据
            return cursor.getLong(0);//返回总记录数
        }
        return 0;                       //如果没数据，返回0
    }
    public int getMaxId(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select max(_id) from tb_inaccount", null);
        if(cursor.moveToLast()){        //访问Cursor中的最后一条数据
            return cursor.getInt(0);//获取访问到的数据
        }
        return 0;                      //如果没数据，返回0
    }
}
