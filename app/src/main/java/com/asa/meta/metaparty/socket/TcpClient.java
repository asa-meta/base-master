package com.asa.meta.metaparty.socket;

import android.util.Log;

import java.io.IOException;

public class TcpClient {
    public static TcpClient mInstance;
    private String TAG = "服务测试<TCP>";

    private TcpSocket tcpSocket;

    private TcpClient() throws IOException {
        tcpSocket = new TcpSocket();
    }

    public static TcpClient getInstance() throws IOException {
        if (mInstance == null) {
            synchronized (TcpClient.class) {
                if (mInstance == null) {
                    mInstance = new TcpClient();
                }
            }
        }
        return mInstance;
    }

    public void send(String json) {
        tcpSocket.sendTcpMessage(json);
    }

    public void receive() {
        tcpSocket.receiveService();
    }

    public void connect(String address, int port) {
        try {
            tcpSocket.startConnect(address, port);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "connect: fail");
        }
    }
}
