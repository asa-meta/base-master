package com.asa.meta.metaparty.model;

import com.asa.meta.rxhttp.cache.model.CacheMode;
import com.asa.meta.rxhttp.main.RxHttp;
import com.asa.meta.rxhttp.subsciber.BaseSubscriber;

public class MainModel {
    private String TAG = getClass().getSimpleName();

    public MainModel() {
    }

    public void loadVersionInfo(BaseSubscriber baseSubscriber) {
        RxHttp.getInstance()
                .get("version/android/2.3.0")
                .cacheMode(CacheMode.FIRSTCACHE)
                .cacheKey("test1")
                .execute()
                .subscribe(baseSubscriber);
    }
}
