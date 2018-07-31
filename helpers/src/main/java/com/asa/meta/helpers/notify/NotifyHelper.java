package com.asa.meta.helpers.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.asa.meta.helpers.R;
import com.asa.meta.helpers.androidOs.OSRomUtils;

import java.util.HashMap;
import java.util.Map;

public class NotifyHelper {
    private Context mContext;
    private int notificationId;
    private Notification notification;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager notificationManager;
    private static final String LOG_TAG = "NotifyHelper";
    private static Map<String, String> mChannelMap = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel buildDefaultChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);//是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED);//小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        return channel;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static NotificationChannel buildProgressChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(false);//是否在桌面icon右上角展示小红点
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        channel.enableVibration(false);
        channel.enableLights(false);
        channel.setSound(null,null);
        return channel;
    }

    public static NotifyHelper buildNotifyHelper(Context context) {
        return new NotifyHelper(context);
    }

    public static void initChannel(Context context,NotificationChannel... notificationChannel) {
        if (OSRomUtils.isOSMore8() && notificationChannel != null) {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
            for (NotificationChannel chanel : notificationChannel) {
                notificationManager.createNotificationChannel(chanel);
            }
        }
    }

    public NotifyHelper(Context mContext) {
        this.mContext = mContext;
        notificationManager = (NotificationManager) mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
    }

    public NotifyHelper setNotificationId(int notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public NotifyHelper setProgressBuilder(String channelId, NotifyInfo notifyInfo) {
        mBuilder = new NotificationCompat.Builder(mContext, channelId);
        if (notifyInfo.pendingIntent != null) {
            mBuilder.setContentIntent(notifyInfo.getPendingIntent());
        }
        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        mBuilder.setSmallIcon(notifyInfo.getSmallIcon());
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), notifyInfo.getSmallIcon()));
        mBuilder.setTicker(notifyInfo.getContent());
        mBuilder.setContentTitle(notifyInfo.getTitle());
        mBuilder.setContentText(notifyInfo.getContent());
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{0});
        return this;
    }

    public NotifyHelper setCompatBuilder(String channelId, NotifyInfo notifyInfo) {
        mBuilder = new NotificationCompat.Builder(mContext, channelId);
        if (notifyInfo.pendingIntent != null) {
            mBuilder.setContentIntent(notifyInfo.getPendingIntent());
        }

        mBuilder.setSmallIcon(notifyInfo.getSmallIcon());
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), notifyInfo.getSmallIcon()));
        mBuilder.setTicker(notifyInfo.getContent());
        mBuilder.setContentTitle(notifyInfo.getTitle());
        mBuilder.setContentText(notifyInfo.getContent());
        mBuilder.setAutoCancel(true);
        int defaults = 0;

        if (notifyInfo.isSound()) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (notifyInfo.isVibrate()) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (notifyInfo.isLights()) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        mBuilder.setDefaults(defaults);
        return this;
    }

    //根据id清除通知
    public static void clear(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public void notifyProgress(int progress) {
        mBuilder.setProgress(100, progress, false);
        sent();
    }

    public void notifyProgressEnd() {
        mBuilder.setProgress(0, 0, false);
        sent();
    }

    public void notifyNormal() {
        sent();
    }

    public Notification getNotification() {
        notification = mBuilder.build();
        return notification;
    }

    public NotifyHelper setNotifyBuilder(String channelId, NotifyInfo notifyInfo) {
        setCompatBuilder(channelId, notifyInfo);
        return this;
    }

    // 发送通知
    public void sent() {
        notification = mBuilder.build();
        notificationManager.notify(notificationId, notification);
    }

    public void clear() {
        notificationManager.cancel(notificationId);
    }

    public void clearAll() {
        notificationManager.cancelAll();
    }


    public final static class NotifyInfo {
        private int smallIcon;
        PendingIntent pendingIntent;
        private String title = "";
        private boolean isSound = true;
        private boolean isVibrate = true;
        private boolean isLights = true;
        private String content = "";

        public PendingIntent getPendingIntent() {
            return pendingIntent;
        }

        public NotifyInfo setPendingIntent(PendingIntent pendingIntent) {
            this.pendingIntent = pendingIntent;
            return this;
        }

        public static NotifyInfo build() {
            return new NotifyInfo();
        }

        public int getSmallIcon() {
            return smallIcon;
        }

        public NotifyInfo setSmallIcon(int smallIcon) {
            this.smallIcon = smallIcon;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public NotifyInfo setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public NotifyInfo setContent(String content) {
            this.content = content;
            return this;
        }

        public boolean isSound() {
            return isSound;
        }

        public NotifyInfo setSound(boolean sound) {
            isSound = sound;
            return this;
        }

        public boolean isVibrate() {
            return isVibrate;
        }

        public NotifyInfo setVibrate(boolean vibrate) {
            isVibrate = vibrate;
            return this;
        }

        public boolean isLights() {
            return isLights;
        }

        public NotifyInfo setLights(boolean lights) {
            isLights = lights;
            return this;
        }

    }
}
