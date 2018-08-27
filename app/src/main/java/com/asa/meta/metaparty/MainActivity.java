package com.asa.meta.metaparty;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.asa.meta.helpers.androidOs.OSRomUtils;
import com.asa.meta.helpers.filesUtils.FileUtils;
import com.asa.meta.helpers.filesUtils.InstallApkUtils;
import com.asa.meta.helpers.notify.NotifyHelper;
import com.asa.meta.helpers.notify.NotifySettingUtils;
import com.asa.meta.helpers.service.ServiceUtils;
import com.asa.meta.helpers.toast.ToastUtils;
import com.asa.meta.metaparty.databinding.ActivityMainBinding;
import com.asa.meta.rxhttp.callback.DownloadProgressCallBack;
import com.asa.meta.rxhttp.disposable.DisposableManager;
import com.asa.meta.rxhttp.exception.ApiException;
import com.asa.meta.rxhttp.interfaces.ProgressDialog;
import com.asa.meta.rxhttp.main.RxHttp;
import com.asa.meta.rxhttp.subsciber.JSONObjectSubscriber;
import com.asa.meta.rxhttp.subsciber.ProgressDialogSubscriber;
import com.asa.meta.rxhttp.utils.HttpLog;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements ProgressDialog {

    private android.app.ProgressDialog progressDialog;

    private Context mContext;
    private String TAG = getClass().getSimpleName();
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setOnClickEvent(new OnClickEvent(this));
        progressDialog = new android.app.ProgressDialog(this);
        this.mContext = this;



    }


    @Override
    protected void onDestroy() {
        DisposableManager.getInstance().removeCompositeDisposable(TAG);
        super.onDestroy();
    }

    //下载文件
    private Disposable test3() {
        final NotifyHelper notifyHelper = NotifyController.notifyProgress(MainActivity.this);
        return RxHttp.downLoad("http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.6.0(744)-release.apk")
                .saveName("zhihu.apk")
                .execute(TAG, new DownloadProgressCallBack<String>() {
                    @Override
                    public void onStart() {
                        HttpLog.e("下载开始");
                        mBinding.btnDownload.setText("下载开始");
                        NotifyController.notifyProgressStart(notifyHelper).notifyProgress(0);
                    }

                    @Override
                    public void onError(ApiException e) {
                        HttpLog.e("下载错误" + e.getMessage());
                        mBinding.btnDownload.setText("下载错误");
                        NotifyController.notifyProgressFail(notifyHelper, e.getMessage()).notifyProgressEnd();
                    }

                    private int cacheProgress;
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        if (cacheProgress == progress && contentLength != 100) {
                            return;
                        }

                        cacheProgress = progress;
                        HttpLog.e(progress + "% ");
                        NotifyController.notifyProgressIng(notifyHelper).notifyProgress(progress);
                        mBinding.btnDownload.setText(progress + "%");
                        if (done) {//下载完成
                            HttpLog.e("下载完成");

                        }
                    }

                    @Override
                    public void onComplete(String path) {
                        HttpLog.e("保存的路径" + path);
                        File file = new File(path);
                        if (FileUtils.checkFile(file) && InstallApkUtils.checkApkFile(mContext, file.getPath())) {
                            mBinding.btnDownload.setText("下载完成:点击重新下载");
                            NotifyController.notifyProgressEnd(notifyHelper, mContext, file).notifyProgressEnd();
                            InstallApkUtils.install(mContext, file);
                        }

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
                        mBinding.showTextView.setText(jsonObject.toString());
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
                        mBinding.showTextView.setText(jsonObject.toString());
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


    public class OnClickEvent {
        private Context context;
        private int i;

        public OnClickEvent(Context context) {
            this.context = context;
        }

        public void check(View view) {
            ToastUtils.showToast("手机版本信息：" + OSRomUtils.getSystemInfo().toString());
        }

        public void expNotify(View view) {
            NotifySettingUtils.expandNotification(context);
        }

        public void collNotify(View view) {
            NotifySettingUtils.collapsingNotification(context);
        }

        public void onClickXiaoMi(View view) {
            if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_MIUI)) {
                ToastUtils.showToast("不是小米手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
                return;
            }

            try {
                NotifySettingUtils.openMIUINotifySetting(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void onClickNotify1(View view) {
            i++;
//            NotifyController.notifyTest2(context, "测试1", "测试：" + i);

            ServiceUtils.startService(context, new Intent(context, MyService.class));
        }

        public void checkNotify(View view) {
            mBinding.btnCheckNotify.setText("是否有通知权限：" + NotifySettingUtils.hasNotifyPermission(context));
        }

        public void onClickNotify2(View view) {
            NotifyController.notifyTest3(context, "测试2", "鸟儿啄完稻谷，轻轻梳理着光润的羽毛。\n" +
                    "“现在你爱这稻谷吗？”精灵又取出一束黄澄澄的稻谷。\n" +
                    "鸟儿抬头望着远处的一湾泉水回答：“现在我爱那一湾泉水，我有点渴了。”\n" +
                    "精灵摘下一片树叶，里面盛了一汪泉水。\n" +
                    "鸟儿喝完泉水，准备振翅飞去。");
        }

        public void onClickNotify3(View view) {
            test3();
        }

        public void onClickNotify4(View view) {
            test1();
        }

        public void onClickNotify5(View view) {
            test2();
        }

        public void onClickHuaWei(View view) {
            if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_EMUI)) {
                ToastUtils.showToast("不是华为手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
                return;
            }
            NotifySettingUtils.openOtherNotifySetting(context);

        }

        public void onClickOther(View view) {
            NotifySettingUtils.openOtherNotifySetting(context);
        }

    }
}
