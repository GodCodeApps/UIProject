package com.pym.uiproject.inject.module;

import com.pym.uiproject.service.NewManager;
import com.pym.uiproject.service.VideoServiceManager;
import com.pym.uiproject.service.base.DataLayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * DataLayerModule
 *
 * @author Peng YanMing 2017/4/28
 */
@Module
public class DataLayerModule {
    @Singleton
    @Provides
    public VideoServiceManager provideVideoServiceManager() {
        return new VideoServiceManager();
    }

    @Singleton
    @Provides
    public NewManager provideDoubanManager() {
        return new NewManager();
    }

    @Singleton
    @Provides
    public DataLayer provideDataLayer(VideoServiceManager VideoServiceManager,NewManager doubanManager) {
        return new DataLayer(VideoServiceManager, doubanManager);
    }
}
