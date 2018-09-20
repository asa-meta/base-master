package com.asa.meta.rxhttp.func;


import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class ByteBodyFunc implements Function<ResponseBody, byte[]> {
    @Override
    public byte[] apply(ResponseBody responseBody) throws Exception {
        byte[] json = responseBody.bytes();
        return json;
    }
}
