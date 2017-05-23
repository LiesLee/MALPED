package com.lies.malped.module.main.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.common.base.ui.BaseFragment;

import java.util.List;

/**
 * Created by LiesLee on 16/8/25.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragments = null;

    public MainFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public List<BaseFragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<BaseFragment> fragments) {
        this.fragments = fragments;
    }
}
