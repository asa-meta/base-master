package com.asa.meta.metaparty.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.asa.meta.metaparty.controller.NotifyController;

public class MyService extends Service {
    String TAG = getClass().getSimpleName();

    public MyService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        Notification notification = NotifyController.buildForegroundNotify(this);
        startForeground(NotifyController.notifyDefaultId, notification);

        Log.i(TAG, "onCreate: stop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
