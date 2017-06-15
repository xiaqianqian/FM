package com.xqq.fm.data.local.card.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xqq on 2017/3/25.
 */

public class CardDBOpenHelper extends SQLiteOpenHelper{

    private static String name = "card.db"; //表示数据库的名称
    private static int version = 1; //表示数据库的版本号

    public CardDBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table card(id integer primary key autoincrement, " +
                                            "cardNumber varchar(64), cardType varchar(64))";
        db.execSQL(sql);// 完成数据库的创建
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
