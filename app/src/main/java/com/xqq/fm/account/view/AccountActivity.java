package com.xqq.fm.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.xqq.fm.R;
import com.xqq.fm.account.adapter.ViewPagerAdapter;
import com.xqq.fm.account.presenter.AccountPresenter;
import com.xqq.fm.account.presenter.IAccountPresenter;
import com.xqq.fm.category.view.CategoryActivity;
import com.xqq.fm.daily.view.DailyFragment;
import com.xqq.fm.MainActivity;
import com.xqq.fm.data.local.CalculateData;
import com.xqq.fm.data.local.bill.entity.Bill;
import com.xqq.fm.transfer.view.AddCardActivity;
import com.xqq.fm.transfer.dialog.CardSelectDialog;
import com.xqq.fm.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        IAccountView, IAccountItemListener {

    private PayFragment payFragment;
    private IncomeFragment incomeFragment;
    private IAccountPresenter accountPresenter;

    // TabLayout标签
    private String[] titles = new String[]{"支出", "收入"};
    private List<Fragment> fragments = new ArrayList<>(titles.length);

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PayOrIncomeFragment payOrIncomeFragment;

    // 计算器数据
    private GridView mGridView; //计算器
    private String[] texts = CalculateData.texts;

    private CardSelectDialog dialog;

    public static final int CATEGORY_RESULT = 0;
    public static final int CARD_RESULT = 1;
    public static final int DATE_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 初始化ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_account);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_account);
        viewPager = (ViewPager) findViewById(R.id.view_pager_account);

        // 设置TabLayout标签的显示方式:MODE_SCROLLABLE:滚动，MODE_FIXED：固定
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //循环注入标签
        for (String title : titles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        payFragment = PayFragment.newInstance();
        incomeFragment = IncomeFragment.newInstance();
        fragments.add(payFragment);
        fragments.add(incomeFragment);
        ViewPagerAdapter accountViewPagerAdapter =
                new ViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(accountViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        payOrIncomeFragment = payFragment;// 设置默认为支出Fragment

        mGridView = (GridView) findViewById(R.id.grid_view_calculate);
        // 创建适配器
        ArrayAdapter calculateAdapter = new ArrayAdapter(this,
                R.layout.gridview_item_account, R.id.tv_calculator, texts);
        mGridView.setAdapter(calculateAdapter);
        mGridView.setOnItemClickListener(this);

        accountPresenter = new AccountPresenter(this);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Bill bill = (Bill) bundle.getSerializable("bill");

            if (bill != null) {
                String payOrIncome = bill.getPayOrIncome();
                if (payOrIncome.equals("收入")) {
                    payOrIncomeFragment = incomeFragment;
                    viewPager.setCurrentItem(1);
                    tabLayout.getTabAt(1).select();
                } else {
                    payOrIncomeFragment = payFragment;
                    viewPager.setCurrentItem(0);
                    tabLayout.getTabAt(0).select();
                }
                payOrIncomeFragment.setBill(bill);
            }
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                getCurrentFragment().saveBill();
                finish();
                break;
        }
    }

    public void saveBill(Bill bill) {
        accountPresenter.saveBill(bill);

        Intent intent = new Intent(this, MainActivity.class);
        setResult(DailyFragment.REQUEST_ADD_BILL_SUCCESS, intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, MainActivity.class);
        setResult(DailyFragment.REQUEST_ADD_BILL_FILED, intent);
        finish();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currData = texts[position];
        accountPresenter.count(currData);
    }

    @Override
    public void setMoney(String money) {
        getCurrentFragment().setBillMoney(money);
    }

    @Override
    public void setCalculator(int visible) {
        mGridView.setVisibility(visible);
    }

    @Override
    public void showCategory(String payOrIncome) {
        // 开启种类选择的Activity
        Intent intent = new Intent(AccountActivity.this, CategoryActivity.class);
        intent.putExtra("payOrIncome", payOrIncome);
        startActivityForResult(intent, CATEGORY_RESULT);
    }

    @Override
    public void showCardDialog() {
        dialog = new CardSelectDialog(this, accountPresenter.getAllCard(),
                new CardSelectDialog.IAccountSelectDialogClickListener() {
                    @Override
                    public void addCardOnClick() {
                        // 添加账户，跳转至添加界面
                        Intent intent = new Intent(AccountActivity.this, AddCardActivity.class);
                        startActivityForResult(intent, CARD_RESULT);
                    }

                    @Override
                    public void setCardName(String content) {
                        getCurrentFragment().setBillAccount(content);
                    }
                });
        dialog.show();
    }

    @Override
    public void showDateSelect() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivityForResult(intent, CARD_RESULT);
    }

    @Override
    public void updateBill(Bill bill) {
        accountPresenter.updateBill(bill);

        Intent intent = new Intent(this, MainActivity.class);
        setResult(DailyFragment.REQUEST_ADD_BILL_SUCCESS, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CATEGORY_RESULT:
                setCategory(data);
                break;
            case CARD_RESULT:
                // 通知账户列表更新
                dialog.refreshData(accountPresenter.getAllCard());
                break;
            case DATE_RESULT:
                setDate(data);
                break;
        }
    }

    private void setDate(Intent data) {
        String date;
        try {
            date = (String) data.getExtras().get("date");

        } catch (NullPointerException e) {
            date = DateUtil.getCurrentTime();
        }
        getCurrentFragment().setBillDate(date);
    }

    private void setCategory(Intent data) {

        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }
        String category = (String) bundle.get("category");
        getCurrentFragment().setBillCategory(category);
    }

    private PayOrIncomeFragment getCurrentFragment() {
        int currItem = viewPager.getCurrentItem();
        switch (currItem) {
            case 0:
                return payFragment;
            case 1:
                return incomeFragment;
        }
        return null;
    }

    public interface PayOrIncomeFragment {
        void setBillMoney(String money);

        void setBillCategory(String category);

        void setBillAccount(String card);

        void setBillDate(String date);

        void saveBill();

        void setBill(Bill bill);
    }
}