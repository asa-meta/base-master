package com.asa.meta.basehabit.base;

import android.content.Context;
import android.content.res.Configuration;

import com.asa.meta.helpers.language.LocalManageUtil;

public class LanguageApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LocalManageUtil.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }
}
