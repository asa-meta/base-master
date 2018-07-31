package com.asa.meta.rxhttp.func;


import android.text.TextUtils;

import com.asa.meta.rxhttp.exception.ServerException;

import org.json.JSONObject;

import io.reactivex.functions.Function;

public class JSONCheckFunc implements Function<JSONObject, JSONObject> {

    public JSONCheckFunc(String successName, String errCodeMessage) {
        this.successName = successName;
        this.errCodeMessage = errCodeMessage;
    }


    private static String successName = "success";
    private static String errCodeMessage = "errorCode";

    public JSONCheckFunc() {
    }

    @Override
    public JSONObject apply(JSONObject jsonObject) {

        return jsonObject;
    }
}
