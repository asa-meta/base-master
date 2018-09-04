package com.asa.meta.metaparty.socket;

import android.text.TextUtils;
import android.util.Log;

import com.asa.meta.helpers.stream.StreamUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class TcpSocket {
    private final String TAG = "服务测试<tcp>";
    private Socket mSocket = null;
    private ServerSocket sSocket = null;
    private HashMap<String, Socket> mSocketMap;
    private PrintWriter pw;
    private BufferedReader br;

    public TcpSocket() throws IOException {
        sSocket = new ServerSocket(Consts.SearchPort);
        mSocketMap = new HashMap<>();
    }

    //客户端建立连接
    public void startConnect(final String ip, final int port) throws IOException {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                if (mSocket == null) {
                    mSocket = new Socket(ip, port);
                    mSocket.setKeepAlive(true);
                    mSocket.setTcpNoDelay(true);
                    mSocket.setReuseAddress(true);
                }
                InputStream is = mSocket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                OutputStream os = mSocket.getOutputStream();
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)), true);
                e.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    public void sendTcpMessage(String json) {
        pw.println(json);
        Log.i(TAG, "tcp 消息发送成功...");
    }

    public void receiveService() {
        Disposable disposable = Observable.interval(1000, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        String content = br.readLine();
                        return content;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                    }
                });
    }

    public void startServiceSocket() {
        Disposable disposable = Observable.interval(1000, 100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        Socket rSocket = sSocket.accept();
                        mSocketMap.put(rSocket.getInetAddress().getHostAddress(), rSocket);
                        InputStream is = rSocket.getInputStream();
                        OutputStream os = rSocket.getOutputStream();
                        os.write("我收到了".getBytes());
                        os.flush();
                        String content = StreamUtils.inputStreamToStr(is);
                        return content;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                    }
                });
    }
}
