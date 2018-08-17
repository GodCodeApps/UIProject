package com.pym.uiproject.app.framework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.pym.uiproject.R;
import com.pym.uiproject.app.main.HomeFragment;
import com.pym.uiproject.app.playvideo.PlayVideoFragment;
import com.pym.uiproject.base.BaseViewModel;
import com.pym.uiproject.base.BindingFragment;
import com.pym.uiproject.base.PopFragmentEvent;
import com.pym.uiproject.base.PopToFragmentEvent;
import com.pym.uiproject.base.RxBus;
import com.pym.uiproject.base.StartFragmentEvent;
import com.pym.uiproject.base.StartFragmentWithPopEvent;
import com.pym.uiproject.databinding.FragMainBinding;
import com.pym.uiproject.widget.BottomNavigationViewHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainFragment extends BindingFragment<FragMainBinding,BaseViewModel> {
    private MenuItem menuItem;
    private boolean isResumed;
    private StartFragmentEvent startFragmentEvent;
    private StartFragmentWithPopEvent startFragmentWithPopEvent;
    private PopFragmentEvent popFragmentEvent;
    private PopToFragmentEvent popToFragmentEvent;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    protected void afterCreate(@Nullable Bundle savedInstanceState) {
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(mBinding.bottomNavigation);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                mBinding.viewpager.setCurrentItem(0);
                                break;
//                            case R.id.item_lib:
//                                binding.viewpager.setCurrentItem(1);
//                                break;
//                            case R.id.item_find:
//                                binding.viewpager.setCurrentItem(1);
//                                break;
                            case R.id.item_more:
                                mBinding.viewpager.setCurrentItem(1);
                                break;
                        }
                        return false;
                    }
                });

        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    mBinding.bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mBinding.bottomNavigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setupViewPager(mBinding.viewpager);
        subscribeEvent();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new PlayVideoFragment());
        viewPager.setAdapter(adapter);
    }

    private void subscribeEvent() {
        RxBus.get().toObservable(StartFragmentEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(mCompositeDisposable::add)
                .subscribe(event -> {
                    if (isResumed) {
                        start(event.targetFragment);
                    } else {
                        this.startFragmentEvent = event;
                    }
                });
        RxBus.get().toObservable(StartFragmentWithPopEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(mCompositeDisposable::add)
                .subscribe(event -> {
                    if (isResumed) {
                        startWithPop(event.targetFragment);
                    } else {
                        this.startFragmentWithPopEvent = event;
                    }
                });
        RxBus.get().toObservable(PopFragmentEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(mCompositeDisposable::add)
                .subscribe(event -> {
                    if (isResumed) {
                        pop();
                    } else {
                        this.popFragmentEvent = event;
                    }
                });
        RxBus.get().toObservable(PopToFragmentEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(mCompositeDisposable::add)
                .subscribe(event -> {
                    if (isResumed) {
                        popTo(event.clazz, event.includeTargetFragment);
                    } else {
                        this.popToFragmentEvent = event;
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        isResumed = true;
        if (startFragmentEvent != null) {
            start(startFragmentEvent.targetFragment);
            startFragmentEvent = null;
        } else if (startFragmentWithPopEvent != null) {
            startWithPop(startFragmentWithPopEvent.targetFragment);
            startFragmentWithPopEvent = null;
        } else if (popFragmentEvent != null) {
            pop();
            popFragmentEvent = null;
        } else if (popToFragmentEvent != null) {
            popTo(popToFragmentEvent.clazz, popToFragmentEvent.includeTargetFragment);
            popFragmentEvent = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isResumed = false;
    }

    @Override
    protected void bindViewModel(@Nullable Bundle savedInstanceState) {

    }
}
