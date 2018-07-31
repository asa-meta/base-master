package com.asa.meta.metaparty;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.asa.meta.rxhttp.cookie.CookieManger;
import com.asa.meta.rxhttp.main.RxHttp;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxHttp.init(this);
        RxHttp.getInstance()
                .setCertificates()
                .setCookieStore(new CookieManger(this))
                .debug("Rxhttp", true)
                .setBaseUrl(API.MAIN_URL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(NotifyController.defultChannlId, NotifyController.defultChannlName, NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableVibration(false);
            channel1.enableLights(false);
            channel1.setSound(null,null);
            NotifyHelper.initChannel(this, channel1);
        }

    }
}
