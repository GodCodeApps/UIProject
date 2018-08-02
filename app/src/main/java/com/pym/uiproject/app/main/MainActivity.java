package com.pym.uiproject.app.main;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingActivity;
import com.pym.uiproject.databinding.ActivityMain1Binding;

public class MainActivity extends BindingActivity<ActivityMain1Binding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main1;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new MainFragment()).commit();
    }
}
