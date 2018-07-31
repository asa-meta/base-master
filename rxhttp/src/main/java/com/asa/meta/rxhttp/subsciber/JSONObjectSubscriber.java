package com.asa.meta.rxhttp.subsciber;

import org.json.JSONObject;

public abstract class JSONObjectSubscriber extends BaseSubscriber<JSONObject> {
    public JSONObjectSubscriber(String tag) {
        super(tag);
    }

    @Override
    public void onNext(JSONObject jsonObject) {
        super.onNext(jsonObject);
        if (!isDisposed()) {
            onSuccess(jsonObject);
        }
        onComplete();
    }

    public abstract void onSuccess(JSONObject jsonObject);
}
