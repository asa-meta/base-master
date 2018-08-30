package com.asa.meta.metaparty.socket;

import java.io.IOException;
import java.net.UnknownHostException;

/*
搜索局域网设备
 */
public interface DeviceSearchService {
    //开始搜索
    void startSearch() throws IOException;

    //停止搜索
    void stopSearch();

    //监听搜索反馈
    void startFeedback();

    //设置超时
    Long getTimeOut();

    //停止接收
    void stopReceive() throws UnknownHostException;
}
