package com.asa.meta.rxhttp.utils;

import org.json.JSONObject;

public class DataParser {

    public static boolean parserSuccess(JSONObject jsonObject){
        return jsonObject.optBoolean("success");
    }

    public static String parserErrorCode(JSONObject jsonObject){
        return jsonObject.optString("errorCode");
    }
}
