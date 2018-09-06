package com.asa.meta.rxhttp.func;


import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class StringBodyFunc implements Function<ResponseBody, String> {
    @Override
    public String apply(ResponseBody responseBody) throws Exception {
        String json = responseBody.string();
        return json;
    }
}
