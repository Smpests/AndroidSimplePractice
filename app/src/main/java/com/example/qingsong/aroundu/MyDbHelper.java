package com.example.qingsong.aroundu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qingsong on 2017/9/11.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test_db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "users";
    public MyDbHelper(Context context) {
        super(context,DATABASE_NAME,null,VERSION);
    }
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String strSQL = "create table " + TABLE_NAME + "(userName varchar(20) primary key unique,passWord varchar(20),regDate char(20))";
        sqLiteDatabase.execSQL(strSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //用于数据库版本更新
    }
}
