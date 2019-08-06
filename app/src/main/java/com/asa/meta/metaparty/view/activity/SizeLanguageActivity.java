package com.asa.meta.metaparty.view.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.ActivitySwitchLanguageBinding;
import com.asa.meta.metaparty.view.activity.base.ILogoutActivity;
import com.asa.meta.metaparty.viewmodel.SizeLanguageModel;

public class SizeLanguageActivity extends ILogoutActivity<ActivitySwitchLanguageBinding, SizeLanguageModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {

        return R.layout.activity_size_language;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initView() {
        super.initView();
        viewModel.title.postValue(getString(R.string.language_size));
        viewModel.textSize.postValue(getResources().getDimension(R.dimen.sp_16));
        viewModel.onSeekBarChangeListener.postValue(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float dimension = getResources().getDimension(R.dimen.sp_16);

                        int max = seekBar.getMax();
                        float scalc = ((float) progress * 2 / max);
                        float v = dimension * 0.85f + dimension * 0.15f * scalc;
                        viewModel.textSize.postValue(v);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        int pro = seekBar.getProgress();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {


                    }
                });
    }

    @Override
    public void initViewObservable() {

    }
}
