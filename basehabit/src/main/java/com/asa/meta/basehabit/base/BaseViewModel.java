package com.asa.meta.basehabit.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


public class BaseViewModel implements IBaseViewModel {
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

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
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
