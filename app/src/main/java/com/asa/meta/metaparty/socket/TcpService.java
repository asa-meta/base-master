package com.asa.meta.metaparty.socket;

import java.io.IOException;

public class TcpService {
    public static TcpService mInstance;
    private String TAG = "服务测试<TCP>";

    private TcpSocket tcpSocket;

    private TcpService() throws IOException {
        tcpSocket = new TcpSocket();
    }

    public static TcpService getInstance() throws IOException {
        if (mInstance == null) {
            synchronized (TcpService.class) {
                if (mInstance == null) {
                    mInstance = new TcpService();
                }
            }
        }
        return mInstance;
    }

    public void buildService() {
        tcpSocket.startServiceSocket();
    }

    public void connect(String ip, int port) throws IOException {
        tcpSocket.startConnect(ip, port);
    }
}
