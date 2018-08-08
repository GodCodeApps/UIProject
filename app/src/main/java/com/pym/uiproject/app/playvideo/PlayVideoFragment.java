package com.pym.uiproject.app.playvideo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragPlayvideoBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Peng YanMing on 2018\8\6 0006
 */
public class PlayVideoFragment extends BindingFragment<FragPlayvideoBinding> {

    private TitlePagerAdapter titlePagerAdapter;
    private List<VideoLiveTable.ChannelsBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_playvideo;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        titlePagerAdapter = new TitlePagerAdapter(getChildFragmentManager(), list);
        binding.viewPager.setAdapter(titlePagerAdapter);
        binding.tableLayout.setupWithViewPager(binding.viewPager);
        getDataLayer().getDoubanService()
                .getVideoLiveTable(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                })
                .doOnNext(videoLiveTable -> {
                    if (videoLiveTable != null && videoLiveTable.getDefault_channel() == 0) {
                        List<VideoLiveTable.ChannelsBean> channels = videoLiveTable.getChannels();
                        if (channels != null && channels.size() > 0) {
                            list.clear();
                            list.addAll(channels);
                        }
                    }
                })
                .doOnError(throwable -> {
                    Snackbar.make(binding.tableLayout, throwable.getMessage() + "", Snackbar.LENGTH_LONG).show();
                })
                .doOnComplete(() -> {
                })
                .subscribe(videoLiveTable -> titlePagerAdapter.notifyDataSetChanged(), e -> Snackbar.make(binding.tableLayout,e.getMessage(),Snackbar.LENGTH_LONG).show());
    }
}
