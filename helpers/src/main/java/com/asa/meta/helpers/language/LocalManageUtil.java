package com.asa.meta.helpers.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class LocalManageUtil {

    private static final String TAG = "LocalManageUtil";
    public static HashMap<Integer, String> languageHashMap = new HashMap<>();

    static {
        languageHashMap.put(0, "跟随系统");
        languageHashMap.put(1, "中文");
        languageHashMap.put(2, "台湾");
        languageHashMap.put(3, "加拿大");
        languageHashMap.put(4, "葡萄牙");
        languageHashMap.put(5, "香港");
        languageHashMap.put(6, "英语");
        languageHashMap.put(7, "澳门");
    }

    /**
     * 获取系统的locale
     *
     * @return Locale对象
     */
    public static Locale getSystemLocale(Context context) {
        return LanguageSharePreferences.getInstance().getSystemCurrentLocal();
    }

    /**
     * 获取选择的语言设置
     *
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        switch (LanguageSharePreferences.getInstance().getSelectLanguage()) {
            case 0:
                return getSystemLocale(context);
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.TAIWAN;
            case 3:
                return Locale.CANADA;
            case 4:
                return new Locale("PT", "PT");
            case 5:
                return new Locale("zh", "HK");
            case 6:
                return Locale.ENGLISH;
            case 7:
                return new Locale("zh", "MO");

            default:
                return Locale.ENGLISH;
        }
    }

    public static void saveSelectLanguage(Context context, int select) {
        LanguageSharePreferences.getInstance().saveLanguage(select);
        setApplicationLanguage(context);
    }

    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     */
    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    public static void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        Log.d(TAG, locale.getLanguage());
        LanguageSharePreferences.getInstance().setSystemCurrentLocal(locale);
    }

    /**
     * 获取Context应用语言
     */
    public static Locale getAppLocale(Context context) {
        Locale local;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            local = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            local = context.getResources().getConfiguration().locale;
        }
        return local;
    }

    public static void onConfigurationChanged(Context context) {
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setApplicationLanguage(context);
    }
}
