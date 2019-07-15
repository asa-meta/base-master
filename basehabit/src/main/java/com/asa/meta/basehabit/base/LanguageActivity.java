package com.asa.meta.basehabit.base;

import android.content.Context;

import androidx.databinding.ViewDataBinding;

import com.asa.meta.helpers.language.LocalManageUtil;

public abstract class LanguageActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }
}
