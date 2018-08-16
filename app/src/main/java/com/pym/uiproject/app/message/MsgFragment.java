package com.pym.uiproject.app.message;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragMsgBinding;
import com.pym.uiproject.widget.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MsgFragment extends BindingFragment<FragMsgBinding> {

    private MsgListAdapter simpleAdapter;
    private List<News.BodyBean.ComponentsBean.DataBean> lists;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_msg;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        binding.includeToolbar.Title.setText("视频");
        lists = new ArrayList<>();
        simpleAdapter = new MsgListAdapter(lists);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.setAdapter(simpleAdapter);
    }
}

