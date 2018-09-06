
package com.asa.meta.rxhttp.func;


import com.asa.meta.rxhttp.cache.model.CacheResult;
import com.asa.meta.rxhttp.utils.HttpLog;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * <p>描述：缓存结果转换</p>
 */
public class CacheResultFunc implements Function<CacheResult, String> {
    @Override
    public String apply(@NonNull CacheResult tCacheResult) {
        HttpLog.e("loadCache isFromCache -->" + tCacheResult.isFromCache);
        return tCacheResult.data;
    }
}
