package com.pym.uiproject.base;

import android.app.Application;

import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;
import com.pym.uiproject.inject.component.ApplicationComponent;
import com.pym.uiproject.inject.component.DaggerApplicationComponent;
import com.pym.uiproject.inject.module.ApplicationModule;
import com.pym.uiproject.util.ImageLoader;

/**
 * 自定义 Application
 *
 * @author Peng YanMing 2017/4/25
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        //初始化库
        PlayerLibrary.init(this);
    }

    private void initDagger() {
        ApplicationComponent.Instance.init(DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }
}
