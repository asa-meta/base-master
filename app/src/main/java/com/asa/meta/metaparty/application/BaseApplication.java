package com.asa.meta.metaparty.application;

import com.asa.meta.basehabit.base.LanguageApplication;
import com.asa.meta.metaparty.api.API;
import com.asa.meta.metaparty.controller.NotifyController;
import com.asa.meta.rxhttp.cookie.CookieManger;
import com.asa.meta.rxhttp.main.RxHttp;

public class BaseApplication extends LanguageApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RxHttp.getInstance().setCertificates().setCookieStore(new CookieManger(this))
                .debug("Rxhttp", true)
                .setBaseUrl(API.MAIN_URL);
        NotifyController.initChannel(this);
    }
}
