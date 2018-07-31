package com.asa.meta.rxhttp.transformer;

import com.asa.meta.rxhttp.cache.model.CacheResult;
import com.asa.meta.rxhttp.func.CacheResultFunc;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

public class CacheResultTramsformer implements ObservableTransformer<CacheResult,String> {
    @Override
    public ObservableSource<String> apply(Observable<CacheResult> upstream) {
        return upstream.map(new CacheResultFunc());
    }
}
