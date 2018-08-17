package com.pym.uiproject.app.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pym.uiproject.R;
import com.pym.uiproject.app.main.vm.NewDetialViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.databinding.FragNewDetialBinding;

/**
 * Peng YanMing on 2018\8\3 0003
 */
public class NewDetialFragment extends BindingFragment<FragNewDetialBinding,NewDetialViewModel> {
    public static NewDetialFragment newInstance(Bundle bundle) {
        NewDetialFragment fragment = new NewDetialFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        String html = getArguments().getString("html");
         mViewModel= new NewDetialViewModel(getContext(),this,mBinding,html);
        mViewModel.afterCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.webView.destroy();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.frag_new_detial;
    }
}
