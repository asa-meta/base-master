package com.asa.meta.helpers.notify;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

import com.asa.meta.helpers.os.OSRomUtils;

import java.lang.reflect.Method;

public class NotifySettingUtils {

    //  是否有通知的权限
    public static boolean hasNotifyPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        } else {
            return true;
        }
    }


    public static void collapsingNotification(Context context) {
        @SuppressLint("WrongConstant") Object service = context.getSystemService("statusbar");

        if (null == service) {
            return;
        }

        try {

            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method collapse = null;

            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }

            collapse.setAccessible(true);
            collapse.invoke(service);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static void expandNotification(Context context) {
        @SuppressLint("WrongConstant")
        Object service = context.getSystemService("statusbar");

        if (null == service) {
            return;
        }

        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method expand = null;
            if (sdkVersion <= 16) {
                expand = clazz.getMethod("expand");
            } else {
                /*

                 * Android SDK 16之后的版本展开通知栏有两个接口可以处理

                 * expandNotificationsPanel()

                 * expandSettingsPanel()

                 */

                //expand =clazz.getMethod("expandNotificationsPanel");
                expand = clazz.getMethod("expandSettingsPanel");

            }

            expand.setAccessible(true);
            expand.invoke(service);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

 /*   //华为打开通知设置 com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationSettingsActivity:
     //  Displayed com.huawei.systemmanager/com.huawei.notificationmanager.ui.NotificationAllChannelSettingsActivity
    public static void openEMUINotifySetting(Context mContext) throws ActivityNotFoundException {
        mContext.enforceCallingOrSelfPermission("com.huawei.systemmanager.permission.ACCESS_INTERFACE", null);

        String cls = OSRomUtils.isAndroid8() ? "com.huawei.notificationmanager.ui.NotificationAllChannelSettingsActivity" : "com.huawei.notificationmanager.ui.NotificationSettingsActivity";
        ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationSettingsActivity");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        intent.setAction("huawei.intent.action.NOTIFICATIONSETTING");
        intent.putExtra("appName", mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()));
        intent.putExtra("packageName", mContext.getPackageName());
        intent.putExtra("from_package", mContext.getPackageName());
        mContext.sendBroadcast(intent, "com.huawei.systemmanager.permission.ACCESS_INTERFACE");
        mContext.startActivity(intent);
    }*/

    //打开通知设置
    public static boolean openNotifySetting(Context mContext) {
        try {
            if (OSRomUtils.getSystemInfo().getOs().equals(OSRomUtils.SYS_MIUI)) {
                openMIUINotifySetting(mContext);
                return true;
            } else {
                openOtherNotifySetting(mContext);
                return true;
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    //小米打开通知设置
    public static void openMIUINotifySetting(Context mContext) throws ActivityNotFoundException {
        ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationFilterActivity");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("appName", mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()));
        intent.putExtra("packageName", mContext.getPackageName());
        mContext.startActivity(intent);
    }

    public static Intent getNotifySettingIntent(Context mContext) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", mContext.getPackageName());
            intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        }
        return intent;
    }


    //打开其他通知设置
    public static void openOtherNotifySetting(Context mContext) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", mContext.getPackageName());
            intent.putExtra("app_uid", mContext.getApplicationInfo().uid);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        }
        mContext.startActivity(intent);
    }


    //小米的神隐模式
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setComponent(new ComponentName("com.miui.powerkeeper","com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
//        startActivityForResult(intent,110);


}
