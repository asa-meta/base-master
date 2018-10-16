package com.asa.meta.basehabit.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

public interface IBaseViewModel extends LifecycleObserver {
//    void initData();

    /**
     * View的界面创建时回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    /**
     * View的界面销毁时回调
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();


    void onActivityResult(int requestCode, int resultCode, Intent data);
}
