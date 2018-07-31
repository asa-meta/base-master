package com.asa.meta.metaparty;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.asa.meta.rxhttp.callback.DownloadProgressCallBack;
import com.asa.meta.rxhttp.disposable.DisposableManager;
import com.asa.meta.rxhttp.exception.ApiException;
import com.asa.meta.rxhttp.interfaces.ProgressDialog;
import com.asa.meta.rxhttp.main.RxHttp;
import com.asa.meta.rxhttp.subsciber.JSONObjectSubscriber;
import com.asa.meta.rxhttp.subsciber.ProgressDialogSubscriber;
import com.asa.meta.rxhttp.utils.HttpLog;

import org.json.JSONObject;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements ProgressDialog {
    private final static String TAG = "MainActivity";
    TextView textView;
    android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new android.app.ProgressDialog(this);
        textView = findViewById(R.id.tv_show);
//        test1();
//
//        test2();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1110);
        }
        test3();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        DisposableManager.getInstance().removeCompositeDisposable(TAG);
        super.onDestroy();
    }

    //下载文件
    private Disposable test3() {
         final NotifyHelper notifyHelper  = NotifyController.notifyProgress(MainActivity.this);
        return RxHttp.downLoad("http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.6.0(744)-release.apk")
                .saveName("zhihu.apk")
                .execute(TAG, new DownloadProgressCallBack<String>() {

                    @Override
                    public void onStart() {
                        HttpLog.e("下载开始");

                    }

                    @Override
                    public void onError(ApiException e) {
                        HttpLog.e("下载错误" + e.getMessage());
                    }

                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        notifyHelper.notifyProgress(progress);
                        if (done) {//下载完成
                            HttpLog.e("下载完成");
                            notifyHelper.notifyProgressEnd();
                        }
                    }

                    @Override
                    public void onComplete(String path) {
                        HttpLog.e("保存的路径" + path);
                    }
                });

    }

    //post
    private void test2() {
        RxHttp.post(API.URL_MEMBER_LOGIN)
                .params("loginName", "13726279075")
                .params("password", "000000")
                .execute()
                .subscribe(new JSONObjectSubscriber(TAG) {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {

                    }

                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    //get
    private void test1() {
        RxHttp.getInstance()
                .get("version/android/2.3.0")
//                .cacheMode(CacheMode.FIRSTCACHE)
//                .cacheKey("test1")
                .execute()
                .subscribe(new ProgressDialogSubscriber(TAG, this) {
                    @Override
                    public void onError(ApiException e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        HttpLog.e(Thread.currentThread().getName());
                        textView.setText(jsonObject.toString());
                    }
                });


    }

    @Override
    public void showProgressDialog() {
        HttpLog.d("----------showProgressDialog---------------");
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        HttpLog.d("----------hideProgressDialog---------------");
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }
}
