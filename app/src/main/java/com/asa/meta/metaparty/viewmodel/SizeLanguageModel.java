package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.helpers.app.AppManager;
import com.asa.meta.helpers.language.LanguageSharePreferences;
import com.asa.meta.metaparty.model.SizeModel;
import com.asa.meta.metaparty.view.activity.MainActivity;

public class SizeLanguageModel extends BaseViewModel {
    public MutableLiveData<Integer> progress = new MutableLiveData();
    public MutableLiveData<Float> textSize = new MutableLiveData();
    public MutableLiveData<Float> fontSizeScale = new MutableLiveData();
    public MutableLiveData<SeekBar.OnSeekBarChangeListener> onSeekBarChangeListener = new MutableLiveData<>();
    SizeModel model = new SizeModel();


    public SizeLanguageModel(@NonNull Application application) {
        super(application);
    }

    public void changeSize() {
        if (fontSizeScale.getValue() > 0) {
            LanguageSharePreferences.getInstance().saveScale(fontSizeScale.getValue());
            AppManager.getAppManager().finishActivity();
            startActivity(MainActivity.class);
        }
    }

}
