package com.asa.meta.metaparty.controller;

import java.util.Date;

public class LogoutController {
    private static LogoutController mInstance;
    private long lastTime;

    private LogoutController() {
    }

    public static LogoutController getInstance() {
        if (mInstance == null) {
            synchronized (LogoutController.class) {
                mInstance = new LogoutController();
            }
        }
        return mInstance;
    }

    ;

    public long getLastTime() {
        if (lastTime <= 0) {
            lastTime = new Date().getTime();
        }

        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }


}
