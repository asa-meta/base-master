package com.asa.meta.helpers.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;

import java.util.Locale;

public class LanguageContextWrapper extends ContextWrapper {
    public static final String PT = "pt";
    public static final String CN = "zh";


    public LanguageContextWrapper(Context base) {
        super(base);
    }

    @SuppressWarnings("deprecation")
    public static ContextWrapper wrap(Context context, String language) {
        if (TextUtils.isEmpty(language)) {
            return new LanguageContextWrapper(context);
        }

        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }
        if (!language.equals("") && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language, language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale);
//                context = context.createConfigurationContext(config);
            } else {
                setSystemLocaleLegacy(config, locale);
//                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        return new LanguageContextWrapper(context);
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config) {
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config) {
        return config.getLocales().get(0);
    }

    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale) {
        config.locale = locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale) {
        config.setLocale(locale);
        config.setLocales(new LocaleList(locale));
    }
}