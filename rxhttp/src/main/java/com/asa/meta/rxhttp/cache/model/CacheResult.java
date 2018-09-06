

package com.asa.meta.rxhttp.cache.model;

import java.io.Serializable;

/**
 * <p>描述：缓存对象</p>
 */
public class CacheResult implements Serializable {
    public boolean isFromCache;
    public String data;

    public CacheResult() {
    }

    public CacheResult(boolean isFromCache) {
        this.isFromCache = isFromCache;
    }

    public CacheResult(boolean isFromCache, String data) {
        this.isFromCache = isFromCache;
        this.data = data;
    }

    public boolean isCache() {
        return isFromCache;
    }

    public void setCache(boolean cache) {
        isFromCache = cache;
    }

    @Override
    public String toString() {
        return "CacheResult{" +
                "isCache=" + isFromCache +
                ", data=" + data +
                '}';
    }
}
