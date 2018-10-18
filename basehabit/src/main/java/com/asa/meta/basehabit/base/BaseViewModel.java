package com.asa.meta.basehabit.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {
    public String TAG = getClass().getSimpleName();
    public UIContextLiveData ucLiveData = new UIContextLiveData();
    public MutableLiveData<String> title = new MutableLiveData<>();
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    public void initData() {

    }


    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

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
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

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

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        ucLiveData.startActivityLiveData.postValue(params);
    }

    public void startService(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        ucLiveData.startServiceLiveData.postValue(params);
    }

    public void showToast(String s) {
        ucLiveData.showToastLiveData.postValue(s);
    }

    public String getString(int resId) {
        return getApplication().getString(resId);
    }

    public String getString(int resId, Object... strings) {
        return getApplication().getString(resId, strings);
    }

    public static class ParameterField {
        //页面跳转,开启服务
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
    }

    public class UIContextLiveData extends LiveData {
        public MutableLiveData<Map<String, Object>> startActivityLiveData = new MutableLiveData<>();
        public MutableLiveData<Map<String, Object>> startServiceLiveData = new MutableLiveData<>();
        public MutableLiveData<String> showToastLiveData = new MutableLiveData<>();
        public MutableLiveData<Boolean> finishActivityLiveData = new MutableLiveData<>();
        public MutableLiveData<String> showDialog = new MutableLiveData<>();
        public MutableLiveData<String> hideDialog = new MutableLiveData<>();
        public MutableLiveData<String> toolbarTitle = new MutableLiveData<>();


    }



}
