package com.asa.meta.metaparty.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;
import com.asa.meta.basehabit.base.LanguageActivity;
import com.asa.meta.helpers.language.LocalManageUtil;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.ActivitySwitchLanguageBinding;
import com.asa.meta.metaparty.viewmodel.SwitchLanguageModel;

public class SwitchLanguageActivity extends LanguageActivity<ActivitySwitchLanguageBinding, SwitchLanguageModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_switch_language;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SwitchLanguageModel initViewModel() {
        return ViewModelProviders.of(this).get(SwitchLanguageModel.class);
    }

    @Override
    public void initView() {
        super.initView();
        viewModel.addItem();
        viewModel.title.postValue("切换语言");
    }

    @Override
    public void initViewObservable() {
        viewModel.switchLanguage.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                LocalManageUtil.saveSelectLanguage(mContext, integer);
                MainActivity.reStart(mContext);
            }
        });
    }
}
