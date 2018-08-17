package com.pym.uiproject.service;

import android.support.v4.app.Fragment;

import com.pym.uiproject.app.main.model.NewHomeInfo;
import com.pym.uiproject.service.base.BaseManager;
import com.pym.uiproject.service.base.DataLayer;

import io.reactivex.Observable;

/**
 * Peng YanMing on 2018\8\17 0017
 */
public class NewManager extends BaseManager implements DataLayer.NewService {

    @Override
    public Observable<NewHomeInfo> getNewHomeList(Fragment fragment, int page) {
        return getApi().getNewListInfo("41657683759", "wifi", "vivo", "32", "video_article", "664", "6.6.4", "android", "a", "vivo+X20A", "vivo", "zh", "25", "7.1.1", "867649036961114", "d38a3a1ca6fafba1", "264", 1080 * 2160, "480", "66404", "1533708507711", "Funtouch+OS_3.2_PD1709_A_1.14.3", page);
    }

//    @Override
//    public Observable<Result> getShow(Fragment fragment, String type, int page) {
////        return getApi().getDoubanShow(type, page)
////                .map(ResponseBody::string)
////                .map(Jsoup::parse)
////                .map(document -> document.select("div[class=thumbnail] div[class=img_single] img"))
////                .flatMap(Observable::fromIterable)
////                .map(element -> element.attr("src"))
////                .map(s -> Image.newImage(fragment, s));
//        return null;
//    }
}
