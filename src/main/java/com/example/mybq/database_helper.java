package com.example.mybq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database_helper extends SQLiteOpenHelper {
    private String CREATE_TABLE = "create table bq(id integer PRIMARY KEY autoincrement,title text,neirong text,time text,color text)";
    private final static int VERSION = 1;

    /**
     * 以context接口建立数据库
     *
     * @param context
     */
    public database_helper(Context context) {
        super(context, "mybq", null, VERSION);
    }

    /**
     * 在db数据库中，建立表bq
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    /**
     * 升级数据库，更改版本为newVersion
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
