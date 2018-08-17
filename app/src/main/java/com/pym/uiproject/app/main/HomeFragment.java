package com.pym.uiproject.app.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import com.pym.uiproject.R;
import com.pym.uiproject.app.main.vm.HomeViewModel;
import com.pym.uiproject.base.BaseViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragHomeBinding;
import com.pym.uiproject.app.main.model.NewTitle;
import java.util.ArrayList;
import java.util.List;
import com.pym.uiproject.app.main.adapter.HomePagerAdapter;
/**
 * Peng YanMing on 2018\8\4 0004
 */
public class HomeFragment extends BindingFragment<FragHomeBinding,HomeViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {
        mViewModel = new HomeViewModel(getContext(), this, mBinding);
        mViewModel.afterCreate();
    }
}
