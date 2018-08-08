package com.pym.uiproject.app.playvideo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pym.uiproject.app.main.FirstFragment;
import com.pym.uiproject.app.main.NewTitle;

import java.util.List;

class TitlePagerAdapter extends FragmentStatePagerAdapter {
    List<VideoLiveTable.ChannelsBean> titleListBeans;
    TitlePagerAdapter(FragmentManager fm, List<VideoLiveTable.ChannelsBean> titleListBeans) {
        super(fm);
        this.titleListBeans=titleListBeans;
    }

    @Override
    public Fragment getItem(int position) {
        return VideoPlayMainFragment.newInstance(titleListBeans.get(position).getId());
    }

    @Override
    public int getCount() {
        return titleListBeans.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  titleListBeans.get(position).getName();
    }
}
