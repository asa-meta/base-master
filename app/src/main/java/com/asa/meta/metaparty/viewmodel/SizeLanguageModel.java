package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.metaparty.model.SizeModel;

public class SizeLanguageModel extends BaseViewModel {
    public MutableLiveData<Integer> progress = new MutableLiveData();
    public MutableLiveData<Float> textSize = new MutableLiveData();
    public MutableLiveData<SeekBar.OnSeekBarChangeListener> onSeekBarChangeListener = new MutableLiveData<>();
    SizeModel model = new SizeModel();


    public SizeLanguageModel(@NonNull Application application) {
        super(application);
    }


}
