package com.asa.meta.metaparty.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.RxWorker;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;

import com.asa.meta.basehabit.bus.RxBus;
import com.asa.meta.helpers.utils.TimeUtils;
import com.asa.meta.metaparty.controller.LogoutController;
import com.asa.meta.metaparty.event.Logout;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class LogoutWorker extends RxWorker {
    private static String LOG_TAG = "LogoutWorker";

    public LogoutWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    public static void start() {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(LogoutWorker.class, 10 * 1000, TimeUnit.MILLISECONDS)
                .addTag(LOG_TAG)
                .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(LOG_TAG, ExistingPeriodicWorkPolicy.KEEP, request);
    }

    public static void finish() {
        WorkManager.getInstance().cancelAllWorkByTag(LOG_TAG);
    }

    @Override
    public Single<Result> createWork() {
        return Single.create(emitter -> {
            Log.i(LOG_TAG, "now:" + TimeUtils.date2String(new Date()) + "  last:" + TimeUtils.millis2String(LogoutController.getInstance().getLastTime()));
            if (new Date().getTime() - LogoutController.getInstance().getLastTime() > 5 * 1000) {
                RxBus.getDefault().post(new Logout());
            }

            if (!emitter.isDisposed()) {
                emitter.onSuccess(Result.success());
            }
        });
    }
}
