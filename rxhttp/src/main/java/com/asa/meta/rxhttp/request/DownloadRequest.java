package com.asa.meta.rxhttp.request;

import com.asa.meta.rxhttp.callback.CallBack;
import com.asa.meta.rxhttp.func.RetryExceptionFunc;
import com.asa.meta.rxhttp.subsciber.DownloadSubscriber;
import com.asa.meta.rxhttp.transformer.HandleErrTransformer;
import com.asa.meta.rxhttp.utils.RxUtil;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <p>描述：下载请求</p>
 */
public class DownloadRequest extends BaseRequest<DownloadRequest> {
    private String savePath;
    private String saveName;

    public DownloadRequest(String url) {
        super(url);
    }

    /**
     * 下载文件路径<br>
     * 默认在：/storage/emulated/0/Android/data/包名/files/1494647767055<br>
     */
    public DownloadRequest savePath(String savePath) {
        this.savePath = savePath;
        return this;
    }

    /**
     * 下载文件名称<br>
     * 默认名字是时间戳生成的<br>
     */
    public DownloadRequest saveName(String saveName) {
        this.saveName = saveName;
        return this;
    }

    public <T> Disposable execute(String tag, CallBack<T> callBack) {
        return (Disposable) build().generateRequest()
                .compose(RxUtil.<ResponseBody>io_new())
                .compose(new HandleErrTransformer())
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay))
                .subscribeWith(new DownloadSubscriber(context, tag, savePath, saveName, callBack));
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiManager.downloadFile(url);
    }
}
