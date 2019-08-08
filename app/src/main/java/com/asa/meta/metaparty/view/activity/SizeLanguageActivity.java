package com.asa.meta.metaparty.view.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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


    protected static final float fontScale = 0.2f;//字體可縮放基數


    @Override
    public void initView() {
        super.initView();
        viewModel.title.postValue(getString(R.string.language_size));
        viewModel.textSize.postValue(getResources().getDimension(R.dimen.sp_16));
        viewModel.progress.postValue(45);
        viewModel.fontSizeScale.postValue(1f);

        viewModel.onSeekBarChangeListener.postValue(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float dimension = getResources().getDimension(R.dimen.sp_16);
                        int max = seekBar.getMax();

                        float scale = ((float) progress * 2 / max);
                        float v = dimension * (1f - fontScale) + dimension * fontScale * scale;
                        float fontSizeScale = v / dimension;
                        Log.d(TAG, "fontSizeScale:" + fontSizeScale);
                        viewModel.fontSizeScale.postValue(fontSizeScale);
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


    /**
     * 重新配置缩放系数
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = 1;//1 设置正常字体大小的倍数
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
