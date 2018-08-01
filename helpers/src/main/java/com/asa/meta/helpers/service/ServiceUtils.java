package com.asa.meta.helpers.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.asa.meta.helpers.androidOs.OSRomUtils;

import java.util.List;

public class ServiceUtils {


    public static void startService(Context context, Intent intent) {
        if (OSRomUtils.isAndroid8()) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    // 判断服务是否正在运行
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
