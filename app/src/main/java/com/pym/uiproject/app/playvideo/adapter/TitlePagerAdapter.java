package com.pym.uiproject.app.playvideo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pym.uiproject.app.playvideo.VideoPlayMainFragment;
import com.pym.uiproject.app.playvideo.model.VideoLiveTable;

import java.util.List;

public class TitlePagerAdapter extends FragmentStatePagerAdapter {
    List<VideoLiveTable.ChannelsBean> titleListBeans;
    public TitlePagerAdapter(FragmentManager fm, List<VideoLiveTable.ChannelsBean> titleListBeans) {
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
