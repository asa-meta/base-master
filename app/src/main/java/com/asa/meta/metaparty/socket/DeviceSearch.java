package com.asa.meta.metaparty.socket;

import java.io.IOException;
import java.net.UnknownHostException;

/*
搜索局域网设备
 */
public interface DeviceSearch {
    //开始搜索
    void startSearch() throws IOException;

    //停止搜索
    void stopSearch();

    //监听搜索反馈
    void startFeedback();

    //停止监听搜索反馈
    void stopFeedback();

    //停止接收
    void stopReceive() throws UnknownHostException;
}
