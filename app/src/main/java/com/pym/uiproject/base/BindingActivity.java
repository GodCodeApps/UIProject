package com.pym.uiproject.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

/**
 * Peng YanMing on 2018\7\30 0030
 */
public abstract class BindingActivity<T extends ViewDataBinding> extends BaseActivity {
    protected T binding;

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected abstract int getLayoutId();
}
