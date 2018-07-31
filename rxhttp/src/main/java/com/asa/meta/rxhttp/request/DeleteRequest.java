package com.asa.meta.rxhttp.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * <p>描述：删除请求</p>
 */
public class DeleteRequest extends BaseRequest<DeleteRequest> {
    public DeleteRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.delete(url, params.urlParamsMap);
    }
}
