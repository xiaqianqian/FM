package com.xqq.fm.category.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xqq on 2017/3/25.
 */

public class CategoryDBOpenHelper extends SQLiteOpenHelper {

    private static String name = "category.db"; //表示数据库的名称
    private static int version = 1; //表示数据库的版本号

    public CategoryDBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table category(id integer primary key autoincrement, " +
                                            "category varchar(64), payOrIncome varchar(64))";
        db.execSQL(sql);// 完成数据库的创建
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
