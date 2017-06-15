package com.xqq.fm.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.xqq.fm.R;
import com.xqq.fm.util.DateUtil;

public class CalendarActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_select_date);

        initView();
    }

    private void initView() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        // 初始化日历
        calendarView.setDate(System.currentTimeMillis());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this, AccountActivity.class);
                intent.putExtra("date", year + "-" + getDate(month + 1) + "-" + getDate(dayOfMonth));
                setResult(AccountActivity.DATE_RESULT, intent);
                finish();
            }
        });
    }

    private String getDate(int content) {
        if (content < 10) {
            return "0" + content;
        }
        return content + "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(CalendarActivity.this, AccountActivity.class);
        intent.putExtra("date", DateUtil.getCurrentTime());
        setResult(AccountActivity.DATE_RESULT, intent);
        finish();
        return true;
    }
}
