package com.asa.meta.metaparty.application;

import android.content.Context;

import com.asa.meta.basehabit.base.LanguageApplication;
import com.asa.meta.helpers.Utils;
import com.asa.meta.helpers.toast.ToastUtils;
import com.asa.meta.metaparty.api.API;
import com.asa.meta.metaparty.controller.NotifyController;
import com.asa.meta.rxhttp.cookie.CookieManger;
import com.asa.meta.rxhttp.main.RxHttp;

public class BaseApplication extends LanguageApplication {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(this);
        RxHttp.init(this);

        RxHttp.getInstance().setCertificates().setCookieStore(new CookieManger(this))
                .debug("Rxhttp", true)
                .setBaseUrl(API.MAIN_URL);
        ToastUtils.init(this);
        NotifyController.initChannel(this);
    }
}
