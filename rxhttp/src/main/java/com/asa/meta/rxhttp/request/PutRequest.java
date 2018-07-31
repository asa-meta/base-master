package com.asa.meta.rxhttp.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * <p>描述：Put请求</p>
 */
public class PutRequest extends BaseBodyRequest<PutRequest> {
    public PutRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (this.object != null) {//自定义的请求object
            return apiManager.putBody(url, object);
        } else {
            return apiManager.put(url, params.urlParamsMap);
        }
    }
}
