package com.pym.uiproject.app.playvideo.vm;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.design.widget.Snackbar;

import com.pym.uiproject.app.playvideo.adapter.VideoLiveListAdapter;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.base.BaseFragment;
import com.pym.uiproject.base.recycler.PagingViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Peng YanMing on 2018\8\16 0016
 */
public class VideoListViewModel extends PagingViewModel<VideoLiveList.HomeDivsBean.HomePartitonBean,VideoLiveListAdapter>{
    private int mId;
    public VideoListViewModel(Context context, BaseFragment fragment, ViewDataBinding binding,int id) {
        super(context, fragment, binding);
        mId=id;
    }

    @Override
    protected void initAdapter() {
        adapter=new VideoLiveListAdapter(mContext.get(),mFragment.get(),mList);
    }

    @Override
    protected void getData(boolean isMore) {
        getDataLayer().getVideoPlayService().getVideoLiveList(mFragment.get(), mId, isMore ? 1 : 0,  isMore ? pagingOffset + 1 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mCompositeDisposable.add(disposable);
                    doOnSubscribe(isMore);
                })
                .doOnNext(videoLiveList -> {
                    pagingHaveMore = videoLiveList.isHas_more();

                })
                .doOnError(throwable -> {
                    doOnError(isMore, throwable);
                })
                .doOnComplete(() -> {
                    doOnComplete(isMore);
                })
                .subscribe(videoLiveTable -> {
                    List<VideoLiveList.HomeDivsBean.HomePartitonBean> list=new ArrayList<>();
                    if (isMore) {
                        if (videoLiveTable == null || videoLiveTable.getHome_divs() == null || videoLiveTable.getHome_divs().size() == 0) {
                            return;
                        }
                    } else {
                        if (videoLiveTable == null || videoLiveTable.getHome_divs() == null || videoLiveTable.getHome_divs().size() == 1) {
                            return;
                        }
                    }
                    if (mId == 1) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveTable.getHome_divs().get(videoLiveTable.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.FeedsInfoBean feeds_info = homeDivsBean.getFeeds_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> home_partiton = feeds_info.getHome_partiton();
                        list.addAll(home_partiton);
                    } else if (mId == 3) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveTable.getHome_divs().get(videoLiveTable.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.HomePartitionInfo homePartitionInfo = homeDivsBean.getHome_partition_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> homePartiton = homePartitionInfo.getHome_partiton();
                        list.addAll(homePartiton);
                    } else if (mId == 15 || mId == 14) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveTable.getHome_divs().get(videoLiveTable.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.HomePartitionInfo homePartitionInfo = homeDivsBean.getHome_partition_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> homePartiton = homePartitionInfo.getHome_partiton();
                        list.addAll(homePartiton);
                    }
                    accept(isMore, list);
                }, e -> Snackbar.make(mBinding.get().getRoot(), e.getMessage(), Snackbar.LENGTH_LONG).show());
    }
}
