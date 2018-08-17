package com.pym.uiproject.app.main.vm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ViewDataBinding;

import com.pym.uiproject.app.main.adapter.NewMutAdapter;
import com.pym.uiproject.app.main.model.ContentGson;
import com.pym.uiproject.app.main.model.NewHomeInfo;
import com.pym.uiproject.base.BaseFragment;
import com.pym.uiproject.base.recycler.PagingViewModel;
import com.pym.uiproject.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Peng YanMing on 2018\8\16 0016
 */
public class FirstViewModel extends PagingViewModel<ContentGson, NewMutAdapter> {
    public FirstViewModel(Context context, BaseFragment fragment, ViewDataBinding binding) {
        super(context, fragment, binding);
    }

    @Override
    protected void initAdapter() {
        adapter = new NewMutAdapter(mContext.get(), mFragment.get(), mList);
    }

    @Override
    public void afterCreate() {
        super.afterCreate();
    }

    @SuppressLint("CheckResult")
    @Override
    protected void getData(boolean isMore) {
        getDataLayer().getDoubanService().getNewHomeList(mFragment.get(), 1)
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mCompositeDisposable.add(disposable);
                    doOnSubscribe(isMore);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    doOnComplete(isMore);
                })
                .doOnError(e -> {
                    doOnError(isMore, e);
                })
                .doOnNext(image -> {
                    pagingHaveMore = image.isHas_more();
                })
                .subscribe(news -> {
                    List<NewHomeInfo.DataBean> data = news.getData();
                    List<ContentGson> results = new ArrayList<>();
                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            NewHomeInfo.DataBean dataBean = data.get(i);
                            String content = dataBean.getContent();
                            ContentGson contentGson = JsonUtil.toObj(content, ContentGson.class);
                            if (contentGson.isHas_image()) {
                                results.add(contentGson);
                            }
                        }
                    }
                    accept(isMore, results);
                }, Throwable::printStackTrace);
    }


}
