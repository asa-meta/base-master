package com.asa.meta.metaparty.view.activity;

import android.os.Bundle;

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
    public void initView() {
        super.initView();
        viewModel.addItem();
        viewModel.title.postValue(getString(R.string.language));
    }

    @Override
    public void initViewObservable() {
        viewModel.switchLanguage.observe(this, integer -> {
            LocalManageUtil.saveSelectLanguage(mContext, integer);
            MainActivity.reStart(mContext);
        });
    }
}
