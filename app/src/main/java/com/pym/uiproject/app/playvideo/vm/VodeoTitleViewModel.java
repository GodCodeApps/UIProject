package com.pym.uiproject.app.playvideo.vm;

import android.content.Context;
import android.support.design.widget.Snackbar;

import com.pym.uiproject.app.playvideo.PlayVideoFragment;
import com.pym.uiproject.app.playvideo.adapter.TitlePagerAdapter;
import com.pym.uiproject.app.playvideo.model.VideoLiveTable;
import com.pym.uiproject.base.FragViewModel;
import com.pym.uiproject.databinding.FragPlayvideoBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Peng YanMing on 2018\8\16 0016
 */
public class VodeoTitleViewModel extends FragViewModel<PlayVideoFragment, FragPlayvideoBinding> {
    private TitlePagerAdapter titlePagerAdapter;
    private List<VideoLiveTable.ChannelsBean> list;
    public VodeoTitleViewModel(Context context, PlayVideoFragment fragment, FragPlayvideoBinding binding) {
        super(context, fragment, binding);
    }

    @Override
    public void afterCreate() {
        list = new ArrayList<>();
        titlePagerAdapter = new TitlePagerAdapter(mFragment.get().getChildFragmentManager(), list);
        mBinding.get().viewPager.setAdapter(titlePagerAdapter);
        mBinding.get().tableLayout.setupWithViewPager(mBinding.get().viewPager);
        getDataLayer().getDoubanService()
                .getVideoLiveTable(mFragment.get())
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
                    Snackbar.make(mBinding.get().tableLayout, throwable.getMessage() + "", Snackbar.LENGTH_LONG).show();
                })
                .doOnComplete(() -> {
                })
                .subscribe(videoLiveTable -> titlePagerAdapter.notifyDataSetChanged(), e -> Snackbar.make(mBinding.get().tableLayout,e.getMessage(),Snackbar.LENGTH_LONG).show());
    }
}
