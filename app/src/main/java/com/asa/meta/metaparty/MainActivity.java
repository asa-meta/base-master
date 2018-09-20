package com.asa.meta.metaparty;

import android.os.Bundle;

import com.asa.meta.basehabit.base.BaseActivity;
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

}
