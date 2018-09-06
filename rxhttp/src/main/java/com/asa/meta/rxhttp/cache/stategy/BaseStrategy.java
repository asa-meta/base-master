package com.asa.meta.rxhttp.cache.stategy;

import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;
import com.asa.meta.rxhttp.utils.HttpLog;

import java.util.ConcurrentModificationException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * <p>描述：实现缓存策略的基类</p>
 */
public abstract class BaseStrategy implements IStrategy {
    Observable<CacheResult> loadCache(final RxCache rxCache, final String key, final long time, final boolean needEmpty) {
        Observable<CacheResult> observable = rxCache.load(key, time).flatMap(new Function<String, ObservableSource<CacheResult>>() {

            @Override
            public ObservableSource<CacheResult> apply(String data) {
                if (data == null) {
                    return Observable.error(new NullPointerException("Not find the cache!"));
                }
                return Observable.just(new CacheResult(true, data));
            }
        });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult>>() {
                        @Override
                        public ObservableSource<? extends CacheResult> apply(@NonNull Throwable throwable) {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }

    //请求成功后：异步保存
    <T> Observable<CacheResult> loadRemote2(final RxCache rxCache, final String key, Observable<String> source, final boolean needEmpty) {
        Observable<CacheResult> observable = source.map(new Function<String, CacheResult>() {
            @Override
            public CacheResult apply(@NonNull String t) {
                HttpLog.i("loadRemote result=" + t.toString());
                rxCache.<String>save(key, t).subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(@NonNull Boolean status) {
                                HttpLog.i("save status => " + status);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) {
                                if (throwable instanceof ConcurrentModificationException) {
                                    HttpLog.i("Save failed, please use a synchronized cache strategy :", throwable);
                                } else {
                                    HttpLog.i(throwable.getMessage());
                                }
                            }
                        });
                return new CacheResult(false, t);
            }
        });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult>>() {
                        @Override
                        public ObservableSource<? extends CacheResult> apply(@NonNull Throwable throwable) {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }

    //请求成功后：同步保存
    <T> Observable<CacheResult> loadRemote(final RxCache rxCache, final String key, Observable<String> source, final boolean needEmpty) {
        Observable<CacheResult> observable = source
                .flatMap(new Function<String, ObservableSource<CacheResult>>() {
                    @Override
                    public ObservableSource<CacheResult> apply(final @NonNull String t) {
                        return rxCache.save(key, t).map(new Function<Boolean, CacheResult>() {
                            @Override
                            public CacheResult apply(@NonNull Boolean aBoolean) {
                                HttpLog.i("save status => " + aBoolean);
                                return new CacheResult(false, t);
                            }
                        }).onErrorReturn(new Function<Throwable, CacheResult>() {
                            @Override
                            public CacheResult apply(@NonNull Throwable throwable) {
                                HttpLog.i("save status => " + throwable);
                                return new CacheResult(false, t);
                            }
                        });
                    }
                });
        if (needEmpty) {
            observable = observable
                    .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends CacheResult>>() {
                        @Override
                        public ObservableSource<? extends CacheResult> apply(@NonNull Throwable throwable) {
                            return Observable.empty();
                        }
                    });
        }
        return observable;
    }
}
