package com.xqq.fm.account.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by xqq on 2017/3/19.
 *
 * 自定义的FragmentPagerAdapter适配器
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private String [] titles;
    private List<Fragment> fragments;
    public ViewPagerAdapter(FragmentManager fragmentManager,
                            String [] titles,
                            List<Fragment> fragments){
        super(fragmentManager);
        this.titles = titles;
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
