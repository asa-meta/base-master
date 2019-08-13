package com.asa.meta.metaparty.model;

import com.asa.meta.rxhttp.cache.model.CacheMode;
import com.asa.meta.rxhttp.callback.DownloadProgressCallBack;
import com.asa.meta.rxhttp.main.RxHttp;
import com.asa.meta.rxhttp.subsciber.BaseSubscriber;

public class HomeModel {
    private String TAG = getClass().getSimpleName();

    public HomeModel() {
    }

    public void loadVersionInfo(BaseSubscriber baseSubscriber) {
        RxHttp.getInstance()
                .get("version/android/2.3.0")
                .cacheMode(CacheMode.FIRSTCACHE)
                .cacheKey("test1")
                .execute()
                .subscribe(baseSubscriber);
    }

    public void downloadApk(DownloadProgressCallBack baseSubscriber) {
        RxHttp.downLoad("http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.6.0(744)-release.apk")
                .saveName("zhihu.apk")
                .execute(TAG, baseSubscriber);
    }
}
