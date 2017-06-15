package com.xqq.fm.category.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xqq.fm.category.model.Category;
import com.xqq.fm.category.model.ICategoryModel;

/**
 * Created by xqq on 2017/3/25.
 */

public class CategoryModel implements ICategoryModel {

    private CategoryDBOpenHelper helper = null;

    public CategoryModel(Context context) {
        helper = new CategoryDBOpenHelper(context);
    }

    @Override
    public void saveCategory(Category category) {
        SQLiteDatabase database = null;

        try {
            String sql = "insert into category(category, payOrIncome) values(?, ?)";
            database = helper.getWritableDatabase();
            database.execSQL(sql, new Object[]{category.getCategory(), category.getPayOrIncome()});
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public String[] getCategoryByPayOrIncome(String payOrIncome) {
        SQLiteDatabase database = null;
        String[] categories = null;
        try {
            String sql = "select category from category where payOrIncome = ?";
            database = helper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sql, new String[]{payOrIncome});

            categories = new String[count(payOrIncome)];
            int position = 0;
            while (cursor.moveToNext()) {
                categories[position++] = cursor.getString(0);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return categories;
    }

    private int count(String payOrIncome) {

        SQLiteDatabase database = helper.getReadableDatabase();
        int count = 0;

        String sql = "select count(category) from category where payOrIncome = ?";

        Cursor cursor = database.rawQuery(sql, new String[]{payOrIncome});

        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}