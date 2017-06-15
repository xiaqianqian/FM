package com.xqq.fm;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xqq.fm.analysis.view.AnalysisFragment;
import com.xqq.fm.daily.view.DailyFragment;
import com.xqq.fm.datamanagement.view.DataManagementFragment;
import com.xqq.fm.login.UserDataOperating;
import com.xqq.fm.transfer.view.TransferFragment;
import com.xqq.fm.util.ActivityUtil;
/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private DailyFragment dailyFragment;  // 日常生活界面也就是主界面
    private AnalysisFragment analysisFragment;  // 统计分析
    private TransferFragment budgetFragment;  // 预算
    private DataManagementFragment dataManagementFragment;  // 数据导入导出

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout; // 侧滑菜单
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private FragmentManager mFragmentManager;

    private TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化控件
        initView();

        // 初始化Fragment
        initFragment();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // 初始化ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        mActionBar.setTitle(R.string.title_daily);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(R.id.nav_daily);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        navigationItemSelectedAction(item);
                        item.setCheckable(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        View header = mNavigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tv_user_name);
        tvUserName.setText(UserDataOperating.getUserName(MainActivity.this));
    }

    private void initFragment() {
        dailyFragment = DailyFragment.newInstance();
        analysisFragment = AnalysisFragment.newInstance();
        budgetFragment = TransferFragment.newInstance();
        dataManagementFragment = DataManagementFragment.newInstance();
        // 获取FragmentManager
        mFragmentManager = getSupportFragmentManager();
        // 默认首页为日常生活
        ActivityUtil.addFragmentToActivity(mFragmentManager, dailyFragment, R.id.layout_fragment);
    }

    // 处理滑动菜单的点击事件
    private void navigationItemSelectedAction(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_daily:
                ActivityUtil.replaceFragment(getSupportFragmentManager()
                        , dailyFragment
                        , R.id.layout_fragment);
                break;
            case R.id.nav_analysis:
                ActivityUtil.replaceFragment(getSupportFragmentManager()
                        , analysisFragment
                        , R.id.layout_fragment);
                break;
            case R.id.nav_budget:
                ActivityUtil.replaceFragment(getSupportFragmentManager()
                        , budgetFragment
                        , R.id.layout_fragment);
                break;
            case R.id.nav_management:
                ActivityUtil.replaceFragment(getSupportFragmentManager()
                        , dataManagementFragment
                        , R.id.layout_fragment);
                break;
        }
        mActionBar.setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}