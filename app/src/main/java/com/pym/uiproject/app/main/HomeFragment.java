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
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager(), list);
        binding.viewPager.setAdapter(homePagerAdapter);
        binding.tableLayout.setPadding(0, 0, 0, 0);
        binding.tableLayout.setupWithViewPager(binding.viewPager);
        binding.tableLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.viewPager.setCurrentItem(0);
        new Thread(runnable).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NewTitle newTitle= (NewTitle) msg.obj;
            List<NewTitle.ShowapiResBodyBean.ChannelListBean> channelList = newTitle.getShowapi_res_body().getChannelList();
            if(channelList!=null&&channelList.size()>0){
            list.clear();
            list.addAll(channelList);
            homePagerAdapter.notifyDataSetChanged();
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final String res = new ShowApiRequest("http://route.showapi.com/109-34", "71701", "a8b6c586636144ed96b9579baaa3d724")
                    .post();
            Gson gson = new Gson();
            NewTitle newBean = gson.fromJson(res, NewTitle.class);
            Message msg = new Message();
            msg.obj = newBean;
            handler.sendMessage(msg);
        }
    };
}
