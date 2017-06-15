package com.xqq.fm.data.local.bill.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xqq on 2017/3/28.
 */

public class BillDBHelper extends SQLiteOpenHelper {
    private static String name = "bill.db"; //表示数据库的名称
    private static int version = 1; //表示数据库的版本号

    public BillDBHelper(Context context) {
        super(context, name, null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table bill(id integer primary key autoincrement," +
                "money double, category varchar(64), cardNumber varchar(64), " +
                "date varchar(64), remark varchar(64), payOrIncome varchar(64))";
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
