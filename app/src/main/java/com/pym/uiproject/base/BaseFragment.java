package com.pym.uiproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pym.uiproject.inject.component.ApplicationComponent;
import com.pym.uiproject.service.base.DataLayer;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * BaseFragment
 *
 * @author Peng YanMing 2016/12/30  2016/12/30
 */
public abstract class BaseFragment extends SupportFragment {
    @Inject
    DataLayer mDataLayer;
    @Inject
    protected CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ApplicationComponent.Instance.get().inject(this);
        afterCreate(savedInstanceState);
    }
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();
    }
    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    /**
     * 提供 Fragment 布局文件
     */
    protected abstract int getLayoutId();

    /**
     * Fragment 创建后
     */
    protected abstract void afterCreate(@Nullable Bundle savedInstanceState);

}
