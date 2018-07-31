package com.asa.meta.rxhttp.cache.stategy;

import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;
import org.json.JSONObject;
import java.util.Arrays;
import io.reactivex.Observable;


/**
 * <p>描述：先请求网络，网络请求失败，再加载缓存</p>
 * <-------此类加载用的是反射 所以类名是灰色的 没有直接引用  不要误删----------------><br>
 */
public final class FirstRemoteStrategy extends BaseStrategy {

    @Override
    public Observable<CacheResult> execute(RxCache rxCache, String cacheKey, long cacheTime, Observable<String> source) {
        Observable<CacheResult> cache = loadCache(rxCache, cacheKey, cacheTime, true);
        Observable<CacheResult> remote = loadRemote(rxCache, cacheKey, source, false);
        //return remote.switchIfEmpty(cache);
        return Observable
                .concatDelayError(Arrays.asList(remote, cache))
                .take(1);
    }
}
