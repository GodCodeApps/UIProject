package com.pym.uiproject.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Peng YanMing on 2018\7\30 0030
 */
public abstract class BindingFragment<T extends ViewDataBinding> extends BaseFragment {

    protected T binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
//         return attachToSwipeBack(binding.getRoot()); // 侧滑返回
        return binding.getRoot();
    }

    protected abstract int getLayoutId();
}

