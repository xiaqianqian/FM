package com.xqq.fm.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xqq on 2017/4/15.
 */

public class UserDataOperating {
    private static final String fileName = "user";

    public static void saveUser(Context context, String userName, String password) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString("userName", "");
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString("password", "");
    }
}
