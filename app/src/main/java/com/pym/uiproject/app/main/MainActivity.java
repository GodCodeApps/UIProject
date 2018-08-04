package com.pym.uiproject.app.main;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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
        // 状态栏深色图标
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
    }





}
