package com.xqq.fm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xqq on 2017/3/22.
 */

public class DateUtil {
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getCurrentMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }

    public static String getCurrentYear() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }

}
