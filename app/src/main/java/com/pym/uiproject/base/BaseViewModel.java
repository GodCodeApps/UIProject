package com.pym.uiproject.base;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.pym.uiproject.inject.component.ApplicationComponent;
import com.pym.uiproject.service.base.DataLayer;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * BaseViewModel
 *
 * @author Peng YanMing 2017/8/11
 */
public abstract class BaseViewModel extends BaseObservable implements Disposable {

    @Inject
    DataLayer mDataLayer;
    @Inject
    protected CompositeDisposable mCompositeDisposable;

    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableBoolean empty = new ObservableBoolean();
    public final ObservableBoolean error = new ObservableBoolean();
    public final ObservableField<String> msg = new ObservableField<>();

    protected WeakReference<Context> mContext;

    public BaseViewModel(Context context) {
        ApplicationComponent.Instance.get().inject(this);
        mContext = new WeakReference<>(context);
    }

    public abstract void afterCreate();

    protected DataLayer getDataLayer() {
        return mDataLayer;
    }

    @Override
    public boolean isDisposed() {
        return mCompositeDisposable.isDisposed();
    }

    @Override
    public void dispose() {
        mCompositeDisposable.dispose();
    }

    /**
     * 刷新提示信息
     */
    protected void notifyMsg(@Nullable String s) {
        if (s != null && s.equals(msg.get())) {
            msg.notifyChange();
        } else {
            msg.set(s);
        }
    }

}
