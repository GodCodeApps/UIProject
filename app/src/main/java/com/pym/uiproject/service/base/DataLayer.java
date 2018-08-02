package com.pym.uiproject.service.base;

import android.media.Image;
import android.support.v4.app.Fragment;

import com.pym.uiproject.app.message.News;
import com.pym.uiproject.app.message.Result;

import io.reactivex.Observable;

/**
 * DataLayer
 *
 * @author Peng YanMing 2017/4/28
 */
public class DataLayer {

    private GankService mGankService;
    private DoubanService mDoubanService;


    public DataLayer(GankService gankService,DoubanService doubanService) {
        mGankService = gankService;
        mDoubanService = doubanService;
    }


    public GankService getGankService() {
        return mGankService;
    }

    public DoubanService getDoubanService() {
        return mDoubanService;
    }


    public interface GankService {
        Observable<Image> getImage(Fragment fragment, int limit, int no);
    }

    public interface DoubanService {
        Observable<News> getRank(Fragment fragment, int page);
        Observable<News> getNewInfo(String sign);
        Observable<Result> getShow(Fragment fragment, String type, int page);
    }
}
