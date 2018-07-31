package com.asa.meta.metaparty;

import android.app.NotificationChannel;
import android.content.Context;

import com.asa.meta.helpers.notify.NotifyHelper;

public class NotifyController {
    public static final String defultChannlId = "123123";
    public static final String defultChannlName = "456789";
    public static final int notifyDefaultId = defultChannlId.hashCode();


    public static final String test2Id = "31411";
    public static final int test2NotifyId = test2Id.hashCode();

    public static final String test3Id = "313515";
    public static final int test3NotifyId = test3Id.hashCode();


    public static void initChannel(Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = NotifyHelper.buildProgressChannel(NotifyController.defultChannlId, NotifyController.defultChannlName);
            NotificationChannel channel2 = NotifyHelper.buildProgressChannel(NotifyController.test2Id, NotifyController.test2Id);
            NotificationChannel channel3 = NotifyHelper.buildProgressChannel(NotifyController.test3Id, NotifyController.test3Id);

            NotifyHelper.initChannel(context, channel1,channel2,channel3);
        }

    }

    public static NotifyHelper notifyDefault(Context context,String channlId,String title,String content) {
        return NotifyHelper.buildNotifyHelper(context).
                setNotificationId(channlId.hashCode()).setCompatBuilder(channlId,NotifyHelper.NotifyInfo.build().
                setTitle(title).setContent(content).
                setSmallIcon(R.mipmap.ic_launcher_round));
    }


    public static void notifyTest2(Context context,String title,String content) {
         notifyDefault(context,test2Id,title,content).sent();
    }

    public static void notifyTest3(Context context,String title,String content) {
         notifyDefault(context,test3Id,title,content).sent();
    }


    public static NotifyHelper notifyProgress(Context context) {
        return NotifyHelper.buildNotifyHelper(context).
                setNotificationId(notifyDefaultId);
    }



    public static NotifyHelper notifyProgressStart(NotifyHelper notifyHelper) {
        return notifyHelper.setProgressBuilder(defultChannlId, NotifyHelper.NotifyInfo.build().
                setTitle("下载知乎安装包").setContent("下载开始").
                setSmallIcon(R.mipmap.ic_launcher_round));
    }
    public static NotifyHelper notifyProgressIng(NotifyHelper notifyHelper) {
        return notifyHelper.setProgressBuilder(defultChannlId, NotifyHelper.NotifyInfo.build().
                setTitle("下载知乎安装包").setContent("下载中").
                setSmallIcon(R.mipmap.ic_launcher_round));
    }


    public static NotifyHelper notifyProgressEnd(NotifyHelper notifyHelper) {
        return notifyHelper.setProgressBuilder(defultChannlId, NotifyHelper.NotifyInfo.build().
                setTitle("下载知乎安装包").setContent("下载完成").
                setSmallIcon(R.mipmap.ic_launcher_round));
    }

    public static NotifyHelper notifyProgressFail(NotifyHelper notifyHelper,String errorCode) {
        return notifyHelper.setProgressBuilder(defultChannlId, NotifyHelper.NotifyInfo.build().
                setTitle("下载知乎安装包").setContent("下载失败:"+errorCode).
                setSmallIcon(R.mipmap.ic_launcher_round));
    }
}
