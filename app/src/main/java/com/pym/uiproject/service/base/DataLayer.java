package com.pym.uiproject.service.base;

import android.support.v4.app.Fragment;

import com.pym.uiproject.app.main.model.NewHomeInfo;
import com.pym.uiproject.app.playvideo.model.VideoLiveList;
import com.pym.uiproject.app.playvideo.model.VideoLiveTable;

import io.reactivex.Observable;

/**
 * DataLayer
 *
 * @author Peng YanMing 2017/4/28
 */
public class DataLayer {

    private VideoPlayService mVideoPlayService;
    private NewService mNewService;


    public DataLayer(VideoPlayService VideoPlayService,NewService NewService) {
        mVideoPlayService = VideoPlayService;
        mNewService = NewService;
    }


    public VideoPlayService getVideoPlayService() {
        return mVideoPlayService;
    }

    public NewService getNewService() {
        return mNewService;
    }


    public interface VideoPlayService {
        Observable<VideoLiveTable> getVideoLiveTable(Fragment fragment);
        Observable<VideoLiveList> getVideoLiveList(Fragment fragment, int id, int feeds_type, int page);
    }
    public interface NewService {
        Observable<NewHomeInfo> getNewHomeList(Fragment fragment, int page);

    }
}
