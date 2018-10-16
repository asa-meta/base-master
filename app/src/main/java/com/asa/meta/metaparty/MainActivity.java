package com.asa.meta.metaparty;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.asa.meta.basehabit.base.BaseActivity;
import com.asa.meta.helpers.files.SharedPreferencesManager;
import com.asa.meta.helpers.language.LanguageContextWrapper;
import com.asa.meta.metaparty.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainAtyModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainAtyModel initViewModel() {
        return new MainAtyModel(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (SharedPreferencesManager.hasValue("test")) {
            super.attachBaseContext(LanguageContextWrapper.wrap(newBase, SharedPreferencesManager.getValue("test")));
        } else {
            super.attachBaseContext(newBase);
        }
        ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
