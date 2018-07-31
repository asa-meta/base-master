package com.asa.meta.metaparty;

import android.app.Application;

import com.asa.meta.helpers.toast.ToastUtils;
import com.asa.meta.rxhttp.cookie.CookieManger;
import com.asa.meta.rxhttp.main.RxHttp;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxHttp.init(this);
        RxHttp.getInstance().setCertificates().setCookieStore(new CookieManger(this))
                .debug("Rxhttp", true)
                .setBaseUrl(API.MAIN_URL);
        ToastUtils.init(this);
        NotifyController.initChannel(this);
    }
}
