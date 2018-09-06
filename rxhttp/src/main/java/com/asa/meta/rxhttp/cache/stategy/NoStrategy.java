

package com.asa.meta.rxhttp.cache.stategy;

import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * <p>描述：网络加载，不缓存</p>
 */
public class NoStrategy implements IStrategy {

    @Override
    public Observable<CacheResult> execute(RxCache rxCache, String cacheKey, long cacheTime, Observable<String> source) {
        return source.map(new Function<String, CacheResult>() {
            @Override
            public CacheResult apply(@NonNull String data) {
                return new CacheResult(false, data);
            }
        });
    }
}
