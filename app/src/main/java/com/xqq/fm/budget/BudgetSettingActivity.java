package com.xqq.fm.budget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.xqq.fm.MainActivity;
import com.xqq.fm.R;
import com.xqq.fm.daily.view.DailyFragment;

public class BudgetSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private Switch switchBudgetControl;
    private LinearLayout llBudgetMoney;
    private EditText etBudgetMoney;
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_setting);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.title_budget_setting);

        switchBudgetControl = (Switch) findViewById(R.id.switch_budget_control);
        llBudgetMoney = (LinearLayout) findViewById(R.id.ll_budget_money);
        etBudgetMoney = (EditText) findViewById(R.id.et_budget_money);
        etBudgetMoney.setText(BudgetSettingDataOperating.saveBudgetMoney(this) + "");

        switchBudgetControl.setOnClickListener(this);

        initView();
    }

    private void initView() {
        isOpen = BudgetSettingDataOperating.getState(this);
        switchBudgetControl.setChecked(isOpen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.add_card_done:
                String text = etBudgetMoney.getText().toString();
                float money = Float.valueOf(text == null ? "0" : text);
                BudgetSettingDataOperating.saveBudgetMoney(money, this);
                break;
        }
        setResult(DailyFragment.REQUEST_BUDGET, new Intent(this, MainActivity.class));
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (isOpen) {
            isOpen = false;
            llBudgetMoney.setVisibility(View.GONE);
        } else {
            isOpen = true;
            llBudgetMoney.setVisibility(View.VISIBLE);
        }
        BudgetSettingDataOperating.saveState(isOpen, this);
    }
}
