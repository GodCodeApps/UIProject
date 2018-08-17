package com.pym.uiproject.app.playvideo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;
import com.pym.uiproject.R;
import com.pym.uiproject.app.playvideo.vm.VodeoTitleViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragPlayvideoBinding;
/**
 * Peng YanMing on 2018\8\6 0006
 */
public class PlayVideoFragment extends BindingFragment<FragPlayvideoBinding,VodeoTitleViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_playvideo;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
    }
    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {
        mViewModel=new VodeoTitleViewModel(getContext(),this,mBinding);
        mViewModel.afterCreate();
        mBinding.setVariable(BR.vm, mViewModel);
    }
}
