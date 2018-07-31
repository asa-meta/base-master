package com.asa.meta.rxhttp.subsciber;

import android.text.TextUtils;

import com.asa.meta.rxhttp.disposable.DisposableManager;
import com.asa.meta.rxhttp.exception.ApiException;
import com.asa.meta.rxhttp.utils.HttpLog;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;


/**
 * <p>描述：订阅的基类</p>
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

//    public BaseSubscriber() {
//    }

    public String tag;

    public BaseSubscriber(String tag) {
        this.tag = tag;
    }

    @Override
    protected void onStart() {
        HttpLog.i("-->http is onStart");
        addDisposable();
    }


    @Override
    public void onNext(@NonNull T t) {
        HttpLog.i("-->http is onNext");

    }

    @Override
    public final void onError(Throwable e) {
        HttpLog.e("-->http is onError");
        if (e instanceof ApiException) {
            HttpLog.e("--> e instanceof ApiException err:" + e);
            onError((ApiException) e);
        } else {
            HttpLog.e("--> e !instanceof ApiException err:" + e);
            onError(ApiException.handleException(e));
        }
        removeDisposable();
        onFinish();
    }

    @Override
    public final void onComplete() {
        HttpLog.i("-->http is onComplete");
        removeDisposable();
        onFinish();
    }


    private void removeDisposable() {
        if (!TextUtils.isEmpty(tag)) {
            CompositeDisposable compositeDisposable = DisposableManager.getInstance().getCompositeDisposable(tag);
            if (compositeDisposable != null) {
                compositeDisposable.remove(this);
            }
        }
    }

    private void addDisposable() {
        if (!TextUtils.isEmpty(tag)) {
            CompositeDisposable compositeDisposable = DisposableManager.getInstance().getCompositeDisposable(tag);
            if (compositeDisposable == null) {
                compositeDisposable = new CompositeDisposable();
                DisposableManager.getInstance().addCompositeDisposable(tag, compositeDisposable);
            }
            compositeDisposable.add(this);
        }
    }

    ;

    public abstract void onError(ApiException e);

    public abstract void onFinish();
}
