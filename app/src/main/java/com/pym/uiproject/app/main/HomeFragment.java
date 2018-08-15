package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.pym.uiproject.R;
import com.pym.uiproject.app.showapi.ShowApiRequest;
import com.pym.uiproject.app.showapi.ui.BigImageAdapter;
import com.pym.uiproject.app.showapi.ui.NewBean;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragHomeBinding;
import com.pym.uiproject.util.DisplayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class HomeFragment extends BindingFragment<FragHomeBinding> {

    private List<NewTitle.ShowapiResBodyBean.ChannelListBean> list;
    private HomePagerAdapter homePagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        init();

    }

    private void init() {
        list = new ArrayList<>();
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean1 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean1.setName("推荐");
        list.add(channelListBean1);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean2 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean2.setName("音乐");
        list.add(channelListBean2);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean3 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean3.setName("影视");
        list.add(channelListBean3);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean4 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean4.setName("综艺");
        list.add(channelListBean4);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean5 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean5.setName("社会");
        list.add(channelListBean5);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean6 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean6.setName("农人");
        list.add(channelListBean6);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean7 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean7.setName("游戏");
        list.add(channelListBean7);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean8 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean8.setName("美食");
        list.add(channelListBean8);
        NewTitle.ShowapiResBodyBean.ChannelListBean channelListBean9 = new NewTitle.ShowapiResBodyBean.ChannelListBean();
        channelListBean9.setName("体育");
        list.add(channelListBean9);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), list);
        binding.viewPager.setAdapter(homePagerAdapter);
        binding.tableLayout.setPadding(0, 0, 0, 0);
        binding.tableLayout.setupWithViewPager(binding.viewPager);
        binding.tableLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.viewPager.setCurrentItem(0);
    }
}
