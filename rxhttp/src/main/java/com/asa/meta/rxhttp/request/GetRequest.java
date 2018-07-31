package com.asa.meta.rxhttp.request;


import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * <p>描述：get请求</p>
 */
public class GetRequest extends BaseRequest<GetRequest> {
    public GetRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.get(url, params.urlParamsMap);
    }
}
