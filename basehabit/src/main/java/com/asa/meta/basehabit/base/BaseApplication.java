package com.asa.meta.basehabit.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.asa.meta.helpers.app.AppManager;

/**
 * Created by goldze on 2017/6/15.
 */

public class BaseApplication extends Application {
    private static BaseApplication sInstance;
    private int started;
    private int stopped;
    private boolean isAppVisible = true;
    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            AppManager.getAppManager().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            ++started;

            if (isApplicationVisible()) {
                isAppVisible = true;
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            ++stopped;

            if (isApplicationInForeground()) {
                isAppVisible = false;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getAppManager().removeActivity(activity);
        }
    };

    /**
     * 获得当前app运行的AppContext
     */
    public static BaseApplication getInstance() {
        return sInstance;
    }

    public boolean isIsAppVisible() {
        return isAppVisible;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //初始化工具类

        //注册监听每个activity的生命周期,便于堆栈式管理
        registerActivityLifecycleCallbacks(mCallbacks);
    }

    public boolean isApplicationVisible() {
        return started > stopped;
    }

    public boolean isApplicationInForeground() {
        return started == stopped;
    }
}
