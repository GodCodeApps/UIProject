package com.pym.uiproject.app.message;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragMsg1Binding;
import com.pym.uiproject.widget.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MsgFragment extends BindingFragment<FragMsg1Binding> {

    private MsgListAdapter simpleAdapter;
    private List<News.BodyBean.ComponentsBean.DataBean> lists;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_msg1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        lists = new ArrayList<>();
        simpleAdapter = new MsgListAdapter(lists);
        binding.recycler1.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler1.setAdapter(simpleAdapter);
        getData(false);
        binding.recycler1.setOnLoadListener(new AutoLoadRecyclerView.OnLoadListener() {
            @Override
            public void onLoad() {
                getData(true);
            }
        });
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false);
            }
        });

    }

    @SuppressLint("CheckResult")
    public void getData(boolean isMore) {
        getDataLayer().getDoubanService().getRank(this, isMore ? binding.recycler1.pageNo + 1 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    binding.recycler1.loadData(isMore);
                    if (isMore) {
                    } else {
                        binding.refreshLayout.setRefreshing(true);
                    }
                })
                .doOnNext(result -> {
                    if (result != null && result.getCode() == 200) {
                        News.BodyBean body = result.getBody();
                        List<News.BodyBean.ComponentsBean.DataBean> data = body.getComponents().get(0).getData();
                        binding.recycler1.haveMore = result != null && !data.isEmpty();
                        if (!isMore) lists.clear();
                        if (body.getComponents().get(0).getData() != null) lists.addAll(data);
                    }
                })
                .doOnError(throwable -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycler1.loading = false;
                    binding.recycler1.pageNo--;
                    Snackbar.make(binding.recycler1, "加载失败", Snackbar.LENGTH_LONG).show();
                })
                .doOnComplete(() -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycler1.loading = false;
                })
                .subscribe(result -> {
                    simpleAdapter.notifyDataSetChanged();
                });

//
//        getDataLayer().getDoubanService().getNewInfo("为了让孩子不尿床 妈妈想出奇招")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(disposable -> {
//                    binding.recycler1.loadData(isMore);
//                    if (isMore) {
//                    } else {
//                        binding.refreshLayout.setRefreshing(true);
//                    }
//                })
//                .doOnNext(result -> {
//                    if (result != null && result.getCode() == 200) {
//                        News.BodyBean body = result.getBody();
//                        List<News.BodyBean.ComponentsBean.DataBean> data = body.getComponents().get(0).getData();
//                        binding.recycler1.haveMore = result != null && !data.isEmpty();
//                        if (!isMore) lists.clear();
//                        if (body.getComponents().get(0).getData() != null) lists.addAll(data);
//                    }
//                })
//                .doOnError(throwable -> {
//                    binding.refreshLayout.setRefreshing(false);
//                    binding.recycler1.loading = false;
//                    binding.recycler1.pageNo--;
//                    Snackbar.make(binding.recycler1, "加载失败", Snackbar.LENGTH_LONG).show();
//                })
//                .doOnComplete(() -> {
//                    binding.refreshLayout.setRefreshing(false);
//                    binding.recycler1.loading = false;
//                })
//                .subscribe(result -> {
//                    simpleAdapter.notifyDataSetChanged();
//                });

    }
}
