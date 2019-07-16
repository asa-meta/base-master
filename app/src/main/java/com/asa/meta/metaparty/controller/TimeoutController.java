package com.asa.meta.metaparty.controller;

public class TimeoutController {
    private static TimeoutController mInstance;
    private long lastTime;

    private TimeoutController() {
    }

    public static TimeoutController getInstance() {
        if (mInstance == null) {
            synchronized (TimeoutController.class) {
                mInstance = new TimeoutController();
            }
        }
        return mInstance;
    }

    ;

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }


}
