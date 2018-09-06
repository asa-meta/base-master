package com.asa.meta.rxhttp.cache.stategy;

import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;

import io.reactivex.Observable;

/**
 * <p>描述：只请求网络</p>
 *<-------此类加载用的是反射 所以类名是灰色的 没有直接引用  不要误删----------------><br>
 */
public final class OnlyRemoteStrategy extends BaseStrategy{

    @Override
    public Observable<CacheResult> execute(RxCache rxCache, String cacheKey, long cacheTime, Observable<String> source) {
        return loadRemote(rxCache, cacheKey, source, false);
    }
}
