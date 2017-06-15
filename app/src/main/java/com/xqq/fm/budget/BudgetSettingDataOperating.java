package com.xqq.fm.budget;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xqq on 2017/4/15.
 */

public class BudgetSettingDataOperating {
    private static final String fileName = "budget";

    public static void saveState(boolean state, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("state", state);
        editor.commit();
    }

    public static void saveBudgetMoney(float money, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("money", money);
        editor.commit();
    }

    public static boolean getState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean("state", false);
    }

    public static float saveBudgetMoney(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getFloat("money", 0.0f);
    }
}
