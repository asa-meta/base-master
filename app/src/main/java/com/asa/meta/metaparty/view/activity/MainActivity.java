package com.asa.meta.metaparty.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.asa.meta.basehabit.base.BaseActivity;
import com.asa.meta.helpers.app.AppManager;
import com.asa.meta.helpers.files.SharedPreferencesManager;
import com.asa.meta.helpers.language.LanguageContextWrapper;
import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.ActivityMainBinding;
import com.asa.meta.metaparty.interfaces.MainClickEvent;
import com.asa.meta.metaparty.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainClickEvent {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainViewModel initViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (SharedPreferencesManager.hasValue("test")) {
            super.attachBaseContext(LanguageContextWrapper.wrap(newBase, SharedPreferencesManager.getValue("test")));
        } else {
            super.attachBaseContext(newBase);
        }
    }


    @Override
    public void changeLanguage() {
        AppManager.getAppManager().keepActivity(MainActivity.class);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
