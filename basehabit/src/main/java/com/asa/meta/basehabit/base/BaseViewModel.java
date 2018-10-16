package com.asa.meta.basehabit.base;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


public class BaseViewModel extends ViewModel implements LifecycleObserver, IBaseViewModel {
    protected Context context;
    protected Fragment fragment;
    public String TAG = "";
    protected Activity activity;

    private BaseViewModel() {
    }

    private BaseViewModel(Context context) {
        this.context = context;
        TAG = getClass().getSimpleName();
    }

    public BaseViewModel(Activity activity) {
        this(activity.getBaseContext());
        this.activity = activity;
    }

    public BaseViewModel(Fragment fragment) {
        this(fragment.getContext());
        this.fragment = fragment;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreate() {
        registerRxBus();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
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
