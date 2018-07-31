package com.asa.meta.rxhttp.disposable;

import com.asa.meta.rxhttp.utils.HttpLog;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;

/*
  Disposableg管理
 */
public final class DisposableManager {
    private static DisposableManager mInstance;
    private ConcurrentHashMap<String, CompositeDisposable> disposableConcurrentHashMap;

    private DisposableManager() {
        disposableConcurrentHashMap = new ConcurrentHashMap<>();
    }

    public static DisposableManager getInstance() {
        if (mInstance == null) {
            synchronized (DisposableManager.class) {
                if (mInstance == null) {
                    mInstance = new DisposableManager();
                }
            }
        }
        return mInstance;
    }

    public CompositeDisposable getCompositeDisposable(String tag) {
        if (this.disposableConcurrentHashMap.containsKey(tag)) {
            return this.disposableConcurrentHashMap.get(tag);
        }
        return null;
    }


    public void addCompositeDisposable(String TAG, CompositeDisposable compositeDisposable) {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            this.disposableConcurrentHashMap.put(TAG, compositeDisposable);
        }
    }

    public void removeCompositeDisposable(String TAG) {
        if (this.disposableConcurrentHashMap.containsKey(TAG)) {
            CompositeDisposable var0 = disposableConcurrentHashMap.get(TAG);
            if (!var0.isDisposed()) {
                var0.dispose();
                HttpLog.e("!!!!!!!!!!!!!" + TAG + "------>dispose()!!!!!!!!!!!!!!!!!!!");
            }
            this.disposableConcurrentHashMap.remove(TAG);
        }

    }
}
