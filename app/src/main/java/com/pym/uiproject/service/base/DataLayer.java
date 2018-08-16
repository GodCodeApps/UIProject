package com.pym.uiproject.service.base;

import android.media.Image;
import android.support.v4.app.Fragment;

import com.pym.uiproject.app.main.NewHomeInfo;
import com.pym.uiproject.app.message.News;
import com.pym.uiproject.app.message.Result;
import com.pym.uiproject.app.playvideo.VideoLiveList;
import com.pym.uiproject.app.playvideo.VideoLiveTable;

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
    // device_id=41657683759
    // &ac=wifi
    // &channel=vivo
    // &aid=32
    // &app_name=video_article
    // &version_code=664
    // &version_name=6.6.4
    // &device_platform=android
    // &ssmix=a
    // &device_type=vivo+X20A
    // &device_brand=vivo
    // &language=zh
    // &os_api=25
    // &os_version=7.1.1
    // &uuid=867649036961114
    // &openudid=d38a3a1ca6fafba1
    // &manifest_version_code=264
    // &resolution=1080*2160
    // &dpi=480
    // &update_version_code=66404
    // &_rticket=1533708507711
    // &rom_version=Funtouch+OS_3.2_PD1709_A_1.14.3
    public interface DoubanService {
        Observable<NewHomeInfo> getNewHomeList(Fragment fragment,int page);
        Observable<VideoLiveTable> getVideoLiveTable(Fragment fragment);
        Observable<VideoLiveList> getVideoLiveList(Fragment fragment,int id,int feeds_type,int page);
        Observable<Result> getShow(Fragment fragment, String type, int page);
    }
}
