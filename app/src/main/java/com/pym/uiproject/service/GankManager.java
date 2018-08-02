package com.pym.uiproject.service;

import android.media.Image;
import android.support.v4.app.Fragment;

import com.pym.uiproject.service.base.BaseManager;
import com.pym.uiproject.service.base.DataLayer;

import io.reactivex.Observable;

/**
 *
 * @author Peng YanMing 2017/4/28
 */
public class GankManager extends BaseManager implements DataLayer.GankService {

    @Override
    public Observable<Image> getImage(Fragment fragment, int limit, int no) {
//        return getApi().getGankImage(limit, no)
//                .map(Result::getResults)
//                .flatMap(Observable::fromIterable)
//                .map(image -> Image.newImage(fragment, image.getUrl()));
        return null;
    }
}
