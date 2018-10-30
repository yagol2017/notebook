package com.example.mybq;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class database {
    private SQLiteDatabase db;
    private database_helper helper = null;
    private ArrayList<databean> bean = new ArrayList<>();
    private databean tempbean = new databean();
    private Cursor cursor;
    private String SELECT_DATA = "select id,title,neirong,time,color from bq";
    private String INSERT_DATA = "insert into bq(title,neirong,time,id,color) values(?,?,?,?,?)";
    private String DELETE_DATA = "delete from bq where id=?";
    private String CHANGE_DATA = "update bq set title=?,neirong=?,time=?,color=? where id=?";
    private String DELETEALL_DATA = "delete from bq";
    private int i = 0;      //bean控制变量
    private tools tools = new tools();

    public database(Context con) {
        helper = new database_helper(con);      //建立数据库
    }

    /**
     * 得到数据库里的数据，添加进bean中
     *
     * @return
     */
    public ArrayList<databean> getBean() {
        db = helper.getWritableDatabase();      //得到数据库的可操控变量
        cursor = db.rawQuery(SELECT_DATA, null);        //数据流指针，得到sql的数据
        cursor.moveToFirst();                   //数据流指针移到第一位
        while (!cursor.isAfterLast()) {
            tempbean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tempbean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            tempbean.setNeirong(cursor.getString(cursor.getColumnIndex("neirong")));
            tempbean.setTime(cursor.getString(cursor.getColumnIndex("time")));
            tempbean.setColor_num(cursor.getString(cursor.getColumnIndex("color")));
            bean.add(tempbean);
            tempbean = new databean();//重初始化定义tempbean，如果不，可能会出现bean中重复数据 TODO：unknow
            cursor.moveToNext();
        }
        db.close();
        helper.close();
        return bean;
    }

    public ArrayList<databean> getBetterBean() {
        ArrayList<databean> abetterbean = new ArrayList<>();
        abetterbean = this.getBean();
        abetterbean = makeBetterBean(abetterbean);
        return abetterbean;
    }

    public ArrayList<databean> makeBetterBean(ArrayList<databean> bean) {
        ArrayList<databean> betterbean = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            betterbean.add(null);
        }
        for (int i = 0; i < bean.size(); i++) {
            betterbean.set(bean.get(i).getId(), bean.get(i));
        }
        return betterbean;
    }


    /**
     * 得到当前数据库ID的最后一位
     */
    public int getLastID() {
        ArrayList<databean> bean = this.getBean();
        if (bean.size() != 0) {
            databean Lastbean = bean.get(bean.size() - 1);
            return Lastbean.getId();
        } else
            return 1;
    }

    public void addBQ(databean databean) {
        db = helper.getWritableDatabase();
        db.execSQL(INSERT_DATA, new String[]{databean.getTitle(), databean.getNeirong(), databean.getTime(), tools.intToString(databean.getId()), databean.getColor_num()});
        db.close();
        helper.close();


    }

    public void deleteBQ(int id) {
        db = helper.getWritableDatabase();
        db.execSQL(DELETE_DATA, new String[]{tools.intToString(id)});
        db.close();
        helper.close();
    }

    public void changeBQ(databean databean) {
        db = helper.getWritableDatabase();
        db.execSQL(CHANGE_DATA, new String[]{databean.getTitle(), databean.getNeirong(), databean.getTime(), databean.getColor_num(), tools.intToString(databean.getId())});
    }

    public databean fromIDtoBean(int id) {
        db = helper.getWritableDatabase();
        cursor = db.rawQuery("select title,neirong,time from bq where id='" + id + "'", null);
        cursor.moveToFirst();
        tempbean.setId(id);
        tempbean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        tempbean.setNeirong(cursor.getString(cursor.getColumnIndex("neirong")));
        tempbean.setTime(cursor.getString(cursor.getColumnIndex("time")));
        db.close();
        helper.close();
        return tempbean;
    }

    public void deleteAll() {
        db = helper.getWritableDatabase();
        db.execSQL(DELETEALL_DATA);
        db.close();
        helper.close();
    }

    public ArrayList<databean> sch_database(String sch) {
        db = helper.getWritableDatabase();      //得到数据库的可操控变量
        cursor = db.rawQuery("select id,title,neirong,time,color from bq where neirong like '%" + sch + "%'", null);        //数据流指针，得到sql的数据
        cursor.moveToFirst();                   //数据流指针移到第一位
        while (!cursor.isAfterLast()) {
            tempbean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tempbean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            tempbean.setNeirong(cursor.getString(cursor.getColumnIndex("neirong")));
            tempbean.setTime(cursor.getString(cursor.getColumnIndex("time")));
            tempbean.setColor_num(cursor.getString(cursor.getColumnIndex("color")));
            bean.add(tempbean);
            tempbean = new databean();//重初始化定义tempbean，如果不，可能会出现bean中重复数据 TODO：unknow
            cursor.moveToNext();
        }
        db.close();
        helper.close();
        return bean;
    }
}
