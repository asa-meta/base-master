package com.asa.meta.metaparty.socket;

import java.io.IOException;

public interface DeviceFeedback {
    //开始反馈
    void startFeedback() throws IOException;

    //结束接受反馈
    void stopFeedback();


}
