package com.pym.uiproject.app.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.databinding.library.baseAdapters.BR;
import com.pym.uiproject.R;
import com.pym.uiproject.app.main.vm.FirstViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragFirstBinding;

/**
 * Peng YanMing on 2018\8\4 0004
 */
public class FirstFragment extends BindingFragment<FragFirstBinding, FirstViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_first;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
    }

    public static Fragment newInstance(String s) {
        FirstFragment firstFragment = new FirstFragment();
        return firstFragment;
    }

    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {
        mViewModel = new FirstViewModel(getContext(), this, mBinding);
        mViewModel.afterCreate();
        mBinding.setVariable(BR.vm, mViewModel);
    }
}
