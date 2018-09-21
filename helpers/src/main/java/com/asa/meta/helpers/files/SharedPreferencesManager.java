package com.asa.meta.helpers.files;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.asa.meta.helpers.Utils;


public class SharedPreferencesManager {

    private final static String MAIN_PREFERENCES_KEY = "SharedPreferencesManager_asa";

    public static boolean putValue(String key, Object object) {
        SharedPreferences sp = getSharedPreferences(Utils.context);
        if (sp == null) {
            return false;
        }

        Editor editor = sp.edit();
        try {
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }
            editor.apply();
        } catch (Exception e) {
            editor.commit();
        }
        return true;
    }

    public static boolean hasValue(String key) {
        SharedPreferences sp = getSharedPreferences(Utils.context);
        return sp.contains(key);
    }

    public static String getValue(String key) {
        SharedPreferences sp = getSharedPreferences(Utils.context);

        return (sp != null) ? sp.getString(key, "") : "";
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(MAIN_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }


}