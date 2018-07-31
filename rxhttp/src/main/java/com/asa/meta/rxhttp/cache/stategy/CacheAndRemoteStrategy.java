package com.asa.meta.rxhttp.cache.stategy;

import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;


/**
 * <p>描述：先显示缓存，再请求网络</p>
 * <-------此类加载用的是反射 所以类名是灰色的 没有直接引用  不要误删----------------><br>
 */
public final class CacheAndRemoteStrategy extends BaseStrategy {
    @Override
    public Observable<CacheResult> execute(RxCache rxCache, String key, long time, Observable source) {
        Observable<CacheResult> cache = loadCache(rxCache, key, time, true);
        Observable<CacheResult> remote = loadRemote(rxCache, key, source, false);
        return Observable.concat(cache, remote)
                .filter(new Predicate<CacheResult>() {
                    @Override
                    public boolean test(@NonNull CacheResult tCacheResult) {
                        return tCacheResult != null && tCacheResult.data != null;
                    }
                });
    }


}
