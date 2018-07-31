package com.asa.meta.rxhttp.func;

import org.json.JSONObject;

import io.reactivex.functions.Function;

public class Sting2JSONObjectFunc implements Function<String,JSONObject> {
    @Override
    public JSONObject apply(String data) throws Exception {
        return new JSONObject(data);
    }
}
