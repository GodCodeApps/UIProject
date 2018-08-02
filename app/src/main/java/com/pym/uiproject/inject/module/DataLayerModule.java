package com.pym.uiproject.inject.module;

import com.pym.uiproject.service.DoubanManager;
import com.pym.uiproject.service.GankManager;
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
    public GankManager provideGankManager() {
        return new GankManager();
    }

    @Singleton
    @Provides
    public DoubanManager provideDoubanManager() {
        return new DoubanManager();
    }

    @Singleton
    @Provides
    public DataLayer provideDataLayer(GankManager gankManager,DoubanManager doubanManager) {
        return new DataLayer(gankManager, doubanManager);
    }
}
