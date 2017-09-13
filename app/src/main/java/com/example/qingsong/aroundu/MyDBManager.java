package com.example.qingsong.aroundu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qingsong on 2017/9/11.
 */

public class MyDBManager {
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDBManager(Context context) {
        this.myDbHelper = new MyDbHelper(context);
        db = myDbHelper.getWritableDatabase();
    }

    //读出数据库内容
    public String execRead(String strSQL) {
        StringBuffer sb = new StringBuffer();
        Cursor cursor = db.rawQuery(strSQL,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sb.append(cursor.getString(0) + "/" + cursor.getString(1) + "/"
                    + cursor.getString(2) + "#");
            //cursor游标移动
            cursor.moveToNext();}
        return sb.deleteCharAt(sb.length()-1).toString();
    }
    public boolean execWrite(final String strSQL) {
        db.beginTransaction();   //批处理开始
        try {
            db.execSQL(strSQL);
            db.setTransactionSuccessful();//批处理添加成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();//处理结束
        }
    }

    public SQLiteDatabase getDB() {
        return db;
    }
}
