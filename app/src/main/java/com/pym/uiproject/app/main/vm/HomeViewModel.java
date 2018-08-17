package com.pym.uiproject.app.main.vm;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.pym.uiproject.app.main.HomeFragment;
import com.pym.uiproject.app.main.adapter.HomePagerAdapter;
import com.pym.uiproject.app.main.model.NewTitle;
import com.pym.uiproject.base.FragViewModel;
import com.pym.uiproject.databinding.FragHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Peng YanMing on 2018\8\16 0016
 */
public class HomeViewModel extends FragViewModel<HomeFragment, FragHomeBinding> {
    private List<NewTitle.ShowapiResBodyBean.ChannelListBean> list;
    private HomePagerAdapter homePagerAdapter;
    public HomeViewModel(Context context, HomeFragment fragment, FragHomeBinding binding) {
        super(context, fragment, binding);
    }

    @Override
    public void afterCreate() {
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
        homePagerAdapter = new HomePagerAdapter(mFragment.get().getChildFragmentManager(), list);
        mBinding.get().viewPager.setAdapter(homePagerAdapter);
        mBinding.get().tableLayout.setPadding(0, 0, 0, 0);
        mBinding.get().tableLayout.setupWithViewPager( mBinding.get().viewPager);
        mBinding.get().tableLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mBinding.get().viewPager.setCurrentItem(0);
    }

}
