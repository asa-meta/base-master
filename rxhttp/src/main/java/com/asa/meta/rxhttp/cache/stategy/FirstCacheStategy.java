
package com.asa.meta.rxhttp.cache.stategy;




import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;

import io.reactivex.Observable;


/**
 * <p>描述：先显示缓存，缓存不存在，再请求网络</p>
 * <-------此类加载用的是反射 所以类名是灰色的 没有直接引用  不要误删----------------><br>
 */
final public class FirstCacheStategy extends BaseStrategy {
    @Override
    public Observable<CacheResult> execute(RxCache rxCache, String key, long time, Observable source) {
        Observable<CacheResult> cache = loadCache(rxCache, key, time, true);
        Observable<CacheResult> remote = loadRemote(rxCache, key, source, false);
        return cache.switchIfEmpty(remote);
    }


}
