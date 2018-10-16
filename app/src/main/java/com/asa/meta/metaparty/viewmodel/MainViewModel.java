package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.helpers.files.FileUtils;
import com.asa.meta.helpers.files.InstallApkUtils;
import com.asa.meta.helpers.files.SharedPreferencesManager;
import com.asa.meta.helpers.notify.NotifyHelper;
import com.asa.meta.helpers.notify.NotifySettingUtils;
import com.asa.meta.helpers.os.OSRomUtils;
import com.asa.meta.helpers.toast.ToastUtils;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.controller.NotifyController;
import com.asa.meta.metaparty.interfaces.MainClickEvent;
import com.asa.meta.metaparty.model.MainModel;
import com.asa.meta.metaparty.socket.UDPService;
import com.asa.meta.rxhttp.callback.DownloadProgressCallBack;
import com.asa.meta.rxhttp.exception.ApiException;
import com.asa.meta.rxhttp.interfaces.ProgressDialog;
import com.asa.meta.rxhttp.main.RxHttp;
import com.asa.meta.rxhttp.subsciber.ProgressDialogSubscriber;
import com.asa.meta.rxhttp.utils.HttpLog;

import org.json.JSONObject;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;

import cn.finalteam.rxgalleryfinal.SimpleRxGalleryFinal;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

public class MainViewModel extends BaseViewModel implements ProgressDialog {
    public MutableLiveData<String> notifyPermissionText = new MutableLiveData<>();
    public MutableLiveData<String> loadingText = new MutableLiveData<>();
    public MutableLiveData<String> showTextView = new MutableLiveData<>();
    public MutableLiveData<String> phonePath = new MutableLiveData<>();
    public MutableLiveData<Drawable> phoneDrawablePath = new MutableLiveData<>();
    public MutableLiveData<String> language = new MutableLiveData<>();
    private MainModel mainModel = new MainModel();
    private MainClickEvent clickEvent;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setClickEvent(MainClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLanguage();
        initGallery();
    }

    @Override
    public void initData() {
        notifyPermissionText.setValue("点击检查通知权限");
        loadingText.setValue("点击下载文件");
        showTextView.setValue("显示数据的view");
        language.setValue("点击切换语言");
    }

    private void initLanguage() {
        if (!SharedPreferencesManager.hasValue("test")) {
            SharedPreferencesManager.putValue("test", "zh");
        }
        language.setValue(getApplication().getString(R.string.language));
        phoneDrawablePath.setValue(ContextCompat.getDrawable(getApplication(), R.drawable.logo));
    }

    private void initGallery() {
        RxGalleryListener.getInstance().setRadioImageCheckedListener(new IRadioImageCheckedListener() {
            @Override
            public void cropAfter(Object t) {
                Log.i(TAG, "cropAfter: " + t.toString());
            }

            @Override
            public boolean isActivityFinish() {
                return false;
            }
        });

        RxGalleryListener
                .getInstance()
                .setMultiImageCheckedListener(
                        new IMultiImageCheckedListener() {
                            @Override
                            public void selectedImg(Object t, boolean isChecked) {
                                Toast.makeText(getApplication(), isChecked ? "选中" : "取消选中", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                                Toast.makeText(getApplication(), "你最多只能选择" + maxSize + "张图片", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    public void changeLanguage() {
        if (SharedPreferencesManager.getValue("test").equals("zh")) {
            SharedPreferencesManager.putValue("test", "pt");
        } else {
            SharedPreferencesManager.putValue("test", "zh");
        }
        language.setValue(getApplication().getString(R.string.language));

        if (clickEvent != null) {
            clickEvent.changeLanguage();
        }
    }

    public void buildSerivce() {
        try {
            UDPService.getInstance().startHeartThread();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void buildClient() {
        try {
            UDPService.getInstance().startClient();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void stopReceiveClient() {
        try {
            UDPService.getInstance().sentStopMessage();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void check() {
        ToastUtils.showToast("手机版本信息：" + OSRomUtils.getSystemInfo().toString());
    }

    public void expNotify() {
        NotifySettingUtils.expandNotification(getApplication());
    }

    public void collNotify() {
        NotifySettingUtils.collapsingNotification(getApplication());
    }

    public void onClickXiaoMi() {
        if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_MIUI)) {
            ToastUtils.showToast("不是小米手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
            return;
        }

        try {
            NotifySettingUtils.openMIUINotifySetting(getApplication());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void checkNotify() {
        notifyPermissionText.setValue("是否有通知权限：" + NotifySettingUtils.hasNotifyPermission(getApplication()));
    }

    public void onClickNotify2() {
        NotifyController.notifyTest3(getApplication(), "测试2", "鸟儿啄完稻谷，轻轻梳理着光润的羽毛。\n" +
                "“现在你爱这稻谷吗？”精灵又取出一束黄澄澄的稻谷。\n" +
                "鸟儿抬头望着远处的一湾泉水回答：“现在我爱那一湾泉水，我有点渴了。”\n" +
                "精灵摘下一片树叶，里面盛了一汪泉水。\n" +
                "鸟儿喝完泉水，准备振翅飞去。");
    }

    public void onClickDownloadAPK() {
        final NotifyHelper notifyHelper = NotifyController.notifyProgress(getApplication());
        RxHttp.downLoad("http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.6.0(744)-release.apk")
                .saveName("zhihu.apk")
                .execute(TAG, new DownloadProgressCallBack<String>() {
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

    public void onClickNotify4() {
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

    public void onClickNotify5() {

    }

    public void onClickHuaWei() {
        if (!OSRomUtils.getSystem().equals(OSRomUtils.SYS_EMUI)) {
            ToastUtils.showToast("不是华为手机，这个手机是：" + OSRomUtils.getSystemInfo().toString());
            return;
        }
        NotifySettingUtils.openOtherNotifySetting(getApplication());
    }

    public void onClickOther() {
        NotifySettingUtils.openOtherNotifySetting(getApplication());
    }

    public void onClickOpenGallery() {
       /* RxGalleryFinalApi.getInstance(activity).openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                Log.i(TAG, "onEvent: " + imageRadioResultEvent.getResult().getOriginalPath());
            }
        });*/
    }

    public void openCamera() {
        /*SimpleRxGalleryFinal.get().init(new SimpleRxGalleryFinal.RxGalleryFinalCropListener() {
            @NonNull
            @Override
            public Activity getSimpleActivity() {
                return activity;
            }

            @Override
            public void onCropCancel() {
                Log.i(TAG, "onCropCancel: ");
            }

            @Override
            public void onCropSuccess(@Nullable Uri uri) {
                Log.i(TAG, "onCropSuccess: " + uri.getPath());
                phonePath.setValue(uri.getPath());
            }

            @Override
            public void onCropError(@NonNull String errorMessage) {
                Log.i(TAG, "onCropError: " + errorMessage);
            }
        }).openCamera();*/
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
