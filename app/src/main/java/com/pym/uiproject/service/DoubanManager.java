package com.pym.uiproject.service;

import android.support.v4.app.Fragment;

import com.pym.uiproject.app.message.News;
import com.pym.uiproject.app.message.Result;
import com.pym.uiproject.service.base.BaseManager;
import com.pym.uiproject.service.base.DataLayer;

import io.reactivex.Observable;

/**
 * @author Peng YanMing 2017/4/28
 */
public class DoubanManager extends BaseManager implements DataLayer.DoubanService {

    @Override
    public Observable<News> getRank(Fragment fragment, int page) {
        return getApi().getNews(page);
    }

    @Override
    public Observable<News> getNewInfo(String sign) {
        return getApi().getNewInfo(sign);
    }

    @Override
    public Observable<Result> getShow(Fragment fragment, String type, int page) {
//        return getApi().getDoubanShow(type, page)
//                .map(ResponseBody::string)
//                .map(Jsoup::parse)
//                .map(document -> document.select("div[class=thumbnail] div[class=img_single] img"))
//                .flatMap(Observable::fromIterable)
//                .map(element -> element.attr("src"))
//                .map(s -> Image.newImage(fragment, s));
        return null;
    }
}
