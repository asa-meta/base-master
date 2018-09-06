package com.asa.meta.basehabit.base;

import android.content.Context;
import android.support.v4.app.Fragment;


public class BaseViewModel implements IBaseViewModel {
    protected Context context;
    protected Fragment fragment;

    public BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        this.context = context;
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
}
