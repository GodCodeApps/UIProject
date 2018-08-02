package com.pym.uiproject.service.base;

import com.google.gson.Gson;
import com.pym.uiproject.inject.component.ApplicationComponent;
import com.pym.uiproject.net.ClientAPI;

import javax.inject.Inject;


/**
 * BaseManager
 *
 * @author Peng YanMing 2017/4/28
 */
public abstract class BaseManager {

    @Inject
    ClientAPI mApi;
    @Inject
    Gson mGson;

    public BaseManager() {
        ApplicationComponent.Instance.get().inject(this);
    }

    public ClientAPI getApi() {
        return mApi;
    }

    public Gson getGson() {
        return mGson;
    }
}
