package com.xqq.fm.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.xqq.fm.R;
import com.xqq.fm.util.DateUtil;

/**
 * Created by xqq on 2017/4/14.
 */

public class DatePopupWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private String[] dates;
    private DatePopupWindowCallback callback;

    public DatePopupWindow(Context context, String[] dates, DatePopupWindowCallback callback) {
        this.callback = callback;
        this.dates = dates;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.date_setting_listview, null, false);

        setContentView(contentView);
        setWidth(240);
        setHeight(400);
        setFocusable(true);

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        setTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));    //要为popWindow设置一个背景才有效

        ListView lv = (ListView) contentView.findViewById(R.id.lv_date_setting);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1
                , dates);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);
    }

    public void showPopupWindow(View view) {
        if (!isShowing()) {
            showAsDropDown(view);
        } else {
            dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String value = dates[position];
        if (value.length() <= 2) {
            callback.setDate(DateUtil.getCurrentYear() + "-" + value);
        } else {
            callback.setDate(value);
        }
        dismiss();
    }

    public interface DatePopupWindowCallback {
        void setDate(String date);
    }
}
