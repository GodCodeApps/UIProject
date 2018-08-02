package com.pym.uiproject.inject.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ApplicationModule
 *
 * @author Peng YanMing 2017/4/28
 */
@Module(includes = {DataLayerModule.class, ClientAPIModule.class, CompositeDisposableModule.class})
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    /**
     * 提供Application单例对象
     *
     * @return Application
     */
    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
