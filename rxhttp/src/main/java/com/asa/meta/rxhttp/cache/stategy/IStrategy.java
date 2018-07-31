
package com.asa.meta.rxhttp.cache.stategy;


import com.asa.meta.rxhttp.cache.RxCache;
import com.asa.meta.rxhttp.cache.model.CacheResult;

import org.json.JSONObject;

import io.reactivex.Observable;

/**
 * <p>描述：实现缓存策略的接口，可以自定义缓存实现方式，只要实现该接口就可以了</p>
 */
public interface IStrategy {

    /**
     * 执行缓存
     *
     * @param rxCache   缓存管理对象
     * @param cacheKey  缓存key
     * @param cacheTime 缓存时间
     * @param source    网络请求对象
     * @return 返回带缓存的Observable流对象
     */

    Observable<CacheResult> execute(RxCache rxCache, String cacheKey, long cacheTime, Observable<String> source);

}
