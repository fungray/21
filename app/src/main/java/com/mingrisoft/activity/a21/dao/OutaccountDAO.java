package com.mingrisoft.activity.a21.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.activity.a21.model.Tb_outaccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/12/29.
 */

public class OutaccountDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;
    public OutaccountDAO(Context context){
        helper = new DBOpenHelper(context);
    }

    public void add(Tb_outaccount tb_outaccount) {
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("insert into tb_outaccount (_id,money,time,type,address,mark) values (?,?,?,?,?,?)",
                new Object[]{ tb_outaccount.get_id(), tb_outaccount.getMoney(), tb_outaccount.getTime(), tb_outaccount.getType(),
                        tb_outaccount.getAddress(), tb_outaccount.getMark() });
    }

    public void update(Tb_outaccount tb_outaccount){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        db.execSQL("update tb_outaccount set money = ?,time = ?,type = ?,address = ?,mark = ? where _id = ?",
                new Object[]{ tb_outaccount.getMoney(), tb_outaccount.getTime(), tb_outaccount.getType(),
                        tb_outaccount.getAddress(), tb_outaccount.getMark() , tb_outaccount.get_id() });
    }

    public Tb_outaccount find(int id){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select _id,money,time,type,address,mark from tb_outaccount where _id = ?",
                new String[]{ String.valueOf(id) });
        if(cursor.moveToNext()){
            //将遍历到的收入信息存储到 Tb_outaccount 类中
            return new Tb_outaccount(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("address")),
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
            db.execSQL("delete from tb_outaccount where _id in ("+sb+")",(Object[]) ids);
        }
    }

    public List<Tb_outaccount> getScrollData(int start, int count){
        List<Tb_outaccount> tb_outaccount = new ArrayList<Tb_outaccount>();
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        //获取所有收入信息
        Cursor cursor = db.rawQuery("select * from tb_outaccount limit ?,?",
                new String[]{ String.valueOf(start), String.valueOf(count) });
        while(cursor.moveToNext()){//便利所有的收入信息
            //将遍历到的收入信息添加到集合中
            tb_outaccount.add(new Tb_outaccount(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("address")),
                    cursor.getString(cursor.getColumnIndex("mark"))));
        }
        return tb_outaccount;
    }

    public long getCount(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount", null);
        if(cursor.moveToNext()){         //判断Cursor中是否有数据
            return cursor.getLong(0);//返回总记录数
        }
        return 0;                       //如果没数据，返回0
    }
    public int getMaxId(){
        db = helper.getWritableDatabase(); //初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount", null);
        if(cursor.moveToLast()){        //访问Cursor中的最后一条数据
            return cursor.getInt(0);//获取访问到的数据
        }
        return 0;                      //如果没数据，返回0
    }
}
