package com.pym.uiproject.app.playvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.databinding.library.baseAdapters.BR;
import com.pym.uiproject.R;
import com.pym.uiproject.app.playvideo.adapter.VideoLiveListAdapter;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.app.playvideo.vm.VideoListViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragVideoMainBinding;

import java.util.List;

/**
 * Peng YanMing on 2018\8\6 0006
 */
public class VideoPlayMainFragment extends BindingFragment<FragVideoMainBinding, VideoListViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_video_main;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
    }

    public static Fragment newInstance(int id) {
        VideoPlayMainFragment fragment = new VideoPlayMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {
        mViewModel = new VideoListViewModel(getContext(), this, mBinding, getArguments().getInt("id"));
        mViewModel.afterCreate();
        mBinding.setVariable(BR.vm, mViewModel);
    }
}
