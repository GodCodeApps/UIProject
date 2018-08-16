package com.pym.uiproject.app.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pym.uiproject.R;
import com.pym.uiproject.base.BindingActivity;
import com.pym.uiproject.databinding.ActivityMainBinding;

public class MainActivity extends BindingActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        MainFragment fragment = findFragment(MainFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fragment, MainFragment.newInstance());
        }
    }
}
