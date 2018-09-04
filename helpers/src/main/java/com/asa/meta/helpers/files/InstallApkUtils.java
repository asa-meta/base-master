package com.asa.meta.helpers.files;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;

public class InstallApkUtils {
    public static void install(Context context, File apkFile) {
        try {
            context.startActivity(getInstallApkIntent(context, apkFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkApkFile(Context context, String apkFilePath) {
        try {
            PackageManager pManager = context.getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkFilePath, PackageManager.GET_ACTIVITIES);
            return pInfo != null ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public static Intent getInstallApkIntent(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProviderUtils.setIntentDataAndType(context, intent, "application/vnd.android.package-archive", apkFile, true);
        return intent;
    }

    public static PendingIntent getInstallApkPendingIntent(Context context, File apkFilePath) {
        return PendingIntent.getActivity(context, 0, getInstallApkIntent(context, apkFilePath), PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
