package com.asa.meta.helpers.language;

import android.content.Context;
import android.content.SharedPreferences;

import com.asa.meta.helpers.Utils;

import java.util.Locale;

public class LanguageSharePreferences {

    private static volatile LanguageSharePreferences instance;
    private final String SP_NAME = "language_setting";
    private final String TAG_LANGUAGE = "language_select";
    private final String TAG_LANGUAGE_SIZE_SCALE = "language_size_scale";
    private final String TAG_SYSTEM_LANGUAGE = "system_language";
    private final SharedPreferences mSharedPreferences;

    private Locale systemCurrentLocal = null;


    public LanguageSharePreferences() {
        mSharedPreferences = Utils.context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static LanguageSharePreferences getInstance() {
        if (instance == null) {
            synchronized (LanguageSharePreferences.class) {
                if (instance == null) {
                    instance = new LanguageSharePreferences();
                }
            }
        }
        return instance;
    }


    public void saveScale(float scale) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putFloat(TAG_LANGUAGE_SIZE_SCALE, scale);
        edit.commit();
    }

    public float getScale() {
        return mSharedPreferences.getFloat(TAG_LANGUAGE_SIZE_SCALE, 0.0f);
    }

    public void saveLanguage(int select) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(TAG_LANGUAGE, select);
        edit.commit();
    }

    public int getSelectLanguage() {
        return mSharedPreferences.getInt(TAG_LANGUAGE, 0);
    }

    public Locale getSystemCurrentLocal() {
        return systemCurrentLocal;
    }

    public void setSystemCurrentLocal(Locale local) {
        systemCurrentLocal = local;
    }
}
