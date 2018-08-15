package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragFirstBinding;
import com.pym.uiproject.util.JsonUtil;
import com.pym.uiproject.widget.AutoLoadRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class FirstFragment extends BindingFragment<FragFirstBinding> {
    private List<ContentGson> results;
    NewMutAdapter bigImageAdapter;
    private static String channelId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.frag_first;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        results = new ArrayList<>();
        bigImageAdapter = new NewMutAdapter(results);
        binding.recycle.setAdapter(bigImageAdapter);
        getData(false);
        binding.recycle.setOnLoadListener(new AutoLoadRecyclerView.OnLoadListener() {
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


    public static Fragment newInstance(String s) {
        channelId = s;
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @SuppressLint("CheckResult")
    public void getData(boolean isMore) {
        getDataLayer().getDoubanService().getNewHomeList(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(news -> {
                    List<NewHomeInfo.DataBean> data = news.getData();
                    if (data != null && data.size() > 0) {
                        if(!isMore){
                            results.clear();
                        }
                        for (int i = 0; i < data.size(); i++) {
                            NewHomeInfo.DataBean dataBean = data.get(i);
                            String content = dataBean.getContent();
                            ContentGson contentGson = JsonUtil.toObj(content, ContentGson.class);
                            if (contentGson.isHas_image()) {
                                results.add(contentGson);
                            }
                        }
                    }
                })
                .doOnError(throwable -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycle.loading = false;
                    Snackbar.make(binding.recycle, "加载失败", Snackbar.LENGTH_LONG).show();
                })
                .doOnComplete(() -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycle.loading = false;
                    binding.recycle.haveMore=true;
                })
                .subscribe(news -> bigImageAdapter.notifyDataSetChanged(), e -> Snackbar.make(binding.recycle, e.getMessage(), Snackbar.LENGTH_LONG).show());
    }
}
