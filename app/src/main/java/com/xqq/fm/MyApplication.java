package com.xqq.fm;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

/**
 * Created by xqq on 2017/3/27.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
