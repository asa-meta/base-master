package com.asa.meta.basehabit.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;


public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {
    public String TAG = "";

    public BaseViewModel(@NonNull Application application) {
        super(application);
        TAG = getClass().getSimpleName();
    }


    public void initData() {

    }


    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        registerRxBus();
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        removeRxBus();
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


}
