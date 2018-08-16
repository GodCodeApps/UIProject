package com.pym.uiproject.app.playvideo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragVideoMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
/**
 * Peng YanMing on 2018\8\6 0006
 */
public class VideoPlayMainFragment extends BindingFragment<FragVideoMainBinding> {
    private int id;
    private List<VideoLiveList.HomeDivsBean.HomePartitonBean> list;
    private VideoLiveListAdapter listAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_video_main;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        init();
    }


    private void init() {
        list = new ArrayList<>();
        binding.recycler.pageNo = 1;
        listAdapter = new VideoLiveListAdapter(getContext(), this, list);
        binding.recycler.setAdapter(listAdapter);
        binding.refreshLayout.setOnRefreshListener(() -> getData(false));
        binding.recycler.setOnLoadListener(() -> getData(true));
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        getData(false);
    }

    public static Fragment newInstance(int id) {
        VideoPlayMainFragment fragment = new VideoPlayMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("CheckResult")
    public void getData(boolean isMore) {
        getDataLayer().getDoubanService().getVideoLiveList(this, id, isMore ? 1 : 0, isMore ? binding.recycler.pageNo + 1 : 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    binding.recycler.loadData(isMore);
                    if (isMore) {
                        binding.recycler.haveMore = true;
                    } else {
                        binding.refreshLayout.setRefreshing(true);
                    }
                })
                .doOnNext(videoLiveList -> {
                    if (isMore) {
                        if (videoLiveList == null || videoLiveList.getHome_divs() == null || videoLiveList.getHome_divs().size() == 0) {
                            return;
                        }
                    } else {
                        if (videoLiveList == null || videoLiveList.getHome_divs() == null || videoLiveList.getHome_divs().size() == 1) {
                            return;
                        }
                    }
                    if (id == 1) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveList.getHome_divs().get(videoLiveList.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.FeedsInfoBean feeds_info = homeDivsBean.getFeeds_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> home_partiton = feeds_info.getHome_partiton();
                        list.addAll(home_partiton);
                    } else if (id == 3) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveList.getHome_divs().get(videoLiveList.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.HomePartitionInfo homePartitionInfo = homeDivsBean.getHome_partition_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> homePartiton = homePartitionInfo.getHome_partiton();
                        list.addAll(homePartiton);
                    } else if (id == 15 || id == 14) {
                        VideoLiveList.HomeDivsBean homeDivsBean = videoLiveList.getHome_divs().get(videoLiveList.getHome_divs().size() - 1);
                        if (!isMore) list.clear();
                        VideoLiveList.HomeDivsBean.HomePartitionInfo homePartitionInfo = homeDivsBean.getHome_partition_info();
                        List<VideoLiveList.HomeDivsBean.HomePartitonBean> homePartiton = homePartitionInfo.getHome_partiton();
                        list.addAll(homePartiton);
                    }
                })
                .doOnError(throwable -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycler.loading = false;
                    binding.recycler.haveMore = true;
                    binding.recycler.pageNo--;
                })
                .doOnComplete(() -> {
                    binding.refreshLayout.setRefreshing(false);
                    binding.recycler.loading = false;
                    binding.recycler.haveMore = true;
                })
                .subscribe(videoLiveTable -> listAdapter.notifyDataSetChanged(), e -> Snackbar.make(binding.recycler, e.getMessage(), Snackbar.LENGTH_LONG).show());
    }

}
