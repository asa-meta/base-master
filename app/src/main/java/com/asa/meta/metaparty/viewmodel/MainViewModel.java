package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.helpers.files.FileUtils;
import com.asa.meta.helpers.files.InstallApkUtils;
import com.asa.meta.helpers.notify.NotifyHelper;
import com.asa.meta.helpers.notify.NotifySettingUtils;
import com.asa.meta.helpers.os.OSRomUtils;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.controller.NotifyController;
import com.asa.meta.metaparty.model.MainModel;
import com.asa.meta.metaparty.view.activity.SwitchLanguageActivity;
import com.asa.meta.rxhttp.callback.DownloadProgressCallBack;
import com.asa.meta.rxhttp.exception.ApiException;
import com.asa.meta.rxhttp.interfaces.ProgressDialog;
import com.asa.meta.rxhttp.subsciber.ProgressDialogSubscriber;
import com.asa.meta.rxhttp.utils.HttpLog;

import org.json.JSONObject;

import java.io.File;

import cn.finalteam.rxgalleryfinal.SimpleRxGalleryFinal;

public class MainViewModel extends BaseViewModel implements ProgressDialog {
    public MutableLiveData<String> notifyPermissionText = new MutableLiveData<>();
    public MutableLiveData<String> loadingText = new MutableLiveData<>();
    public MutableLiveData<String> showTextView = new MutableLiveData<>();
    public MutableLiveData<String> phonePath = new MutableLiveData<>();
    public MutableLiveData<Drawable> phoneDrawablePath = new MutableLiveData<>();
    public MutableLiveData<String> language = new MutableLiveData<>();
    public MutableLiveData<Boolean> openGallery = new MutableLiveData<>();
    public MutableLiveData<Boolean> openCamera = new MutableLiveData<>();
    private MainModel mainModel = new MainModel();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void initData() {
        notifyPermissionText.setValue(getString(R.string.check_permission));
        loadingText.setValue(getString(R.string.downloding_apk));
        showTextView.setValue(getString(R.string.show_text));
        language.setValue(getApplication().getString(R.string.language));
        phoneDrawablePath.setValue(ContextCompat.getDrawable(getApplication(), R.drawable.logo));
    }

    //更换语言
    public void switchLanguage() {
        startActivity(SwitchLanguageActivity.class);
    }

    //检查手机信息
    public void checkPhoneInfo() {
        showToast(getString(R.string.phone_info, OSRomUtils.getSystemInfo().toString()));
    }

    //展开通知栏
    public void expNotify() {
        NotifySettingUtils.expandNotification(getApplication());
    }

    //收缩通知栏
    public void collNotify() {
        NotifySettingUtils.collapsingNotification(getApplication());
    }

    //打开小米通知设置
    public void openMIUINotifySetting() {
        if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_MIUI)) {
            showToast("不是小米手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
            return;
        }

        try {
            NotifySettingUtils.openMIUINotifySetting(getApplication());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //检查是否有通知权限
    public void checkNotifyPermission() {
        notifyPermissionText.setValue("是否有通知权限：" + NotifySettingUtils.hasNotifyPermission(getApplication()));
    }

    //发出第二类型的通知
    public void onClickNotify2() {
        NotifyController.notifyTest3(getApplication(), "测试2", "鸟儿啄完稻谷，轻轻梳理着光润的羽毛。\n" +
                "“现在你爱这稻谷吗？”精灵又取出一束黄澄澄的稻谷。\n" +
                "鸟儿抬头望着远处的一湾泉水回答：“现在我爱那一湾泉水，我有点渴了。”\n" +
                "精灵摘下一片树叶，里面盛了一汪泉水。\n" +
                "鸟儿喝完泉水，准备振翅飞去。");
    }

    //下载apk
    public void downloadAPK() {
        final NotifyHelper notifyHelper = NotifyController.notifyProgress(getApplication());

        mainModel.downloadApk(new DownloadProgressCallBack<String>() {
            private int cacheProgress;

            @Override
            public void onStart() {
                HttpLog.e("下载开始");
                loadingText.setValue("下载开始");
                NotifyController.notifyProgressStart(notifyHelper).notifyProgress(0);
            }

            @Override
            public void onError(ApiException e) {
                HttpLog.e("下载错误" + e.getMessage());
                loadingText.setValue("下载错误");
                NotifyController.notifyProgressFail(notifyHelper, e.getMessage()).notifyProgressEnd();
            }

            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                if (cacheProgress == progress && contentLength != 100) {
                    return;
                }

                cacheProgress = progress;
                HttpLog.e(progress + "% ");
                NotifyController.notifyProgressIng(notifyHelper).notifyProgress(progress);
                loadingText.setValue(progress + "%");

                if (done) {//下载完成
                    HttpLog.e("下载完成");
                }
            }

            @Override
            public void onComplete(String path) {
                HttpLog.e("保存的路径" + path);
                File file = new File(path);
                if (FileUtils.checkFile(file) && InstallApkUtils.checkApkFile(getApplication(), file.getPath())) {
                    loadingText.setValue("下载完成:点击重新下载");
                    NotifyController.notifyProgressEnd(notifyHelper, getApplication(), file).notifyProgressEnd();
                    InstallApkUtils.install(getApplication(), file);
                }
            }
        });

    }

    //获取apk版本信息
    public void methodGet() {
        mainModel.loadVersionInfo(new ProgressDialogSubscriber(TAG, this) {
            @Override
            public void onError(ApiException e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onSuccess(JSONObject jsonObject) {
                HttpLog.e(Thread.currentThread().getName());
                showTextView.setValue(jsonObject.toString());
            }
        });
    }

    //打开华为消息通知设置
    public void openEMUINotifySetting() {
        if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_EMUI)) {
            showToast("不是华为手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
            return;
        }
        NotifySettingUtils.openOtherNotifySetting(getApplication());
    }

    //打开图片画廊
    public void openGallery() {
        openGallery.postValue(true);
    }

    //打开摄像机
    public void openCamera() {
        openCamera.postValue(true);
    }

    //甚至图片路径
    public void setPhonePath(String path) {
        phonePath.postValue(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleRxGalleryFinal.get().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }
}
