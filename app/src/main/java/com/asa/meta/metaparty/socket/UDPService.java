package com.asa.meta.metaparty.socket;

import android.text.TextUtils;
import android.util.Log;

import com.asa.meta.helpers.Utils;
import com.asa.meta.helpers.os.NetUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class UDPService {
    private final int MaxNumber = 200;

    private static UDPService mInstance;
    private DatagramSocket mSocket = null;
    private InetAddress mAddress = null;
    private DatagramPacket cPacket = null;
    private Disposable sDisposable = null;
    private Disposable fDisposable = null;

    private String TAG = "服务测试";
    private List<String> hostList = new ArrayList<>();

    private UDPService() throws SocketException, UnknownHostException {
        UserBean userBean = new UserBean();

        this.mSocket = new DatagramSocket(null);
        this.mSocket.setReuseAddress(true);
        this.mSocket.bind(new InetSocketAddress(Consts.SearchPort));
        this.mAddress = InetAddress.getByName(Consts.localIP);
        userBean.setHostAddress(mSocket.getLocalAddress().getHostAddress());
        userBean.setType(Consts.UDP_KEEP_ACTIVE);

        byte[] sendData = userBean.build().getBytes();

        this.cPacket = new DatagramPacket(sendData, sendData.length, mAddress, Consts.SearchPort);
        this.cPacket.setData(sendData);
    }

    public static UDPService getInstance() throws SocketException, UnknownHostException {
        if (mInstance == null) {
            synchronized (UDPService.class) {
                mInstance = new UDPService();
            }
        }
        return mInstance;
    }


    //UDP心跳包
    public void startHeartThread() {
        //发送包
        Observable.interval(3000, 10 * 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Long, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(Long aLong) throws Exception {
                        hostList.clear();
                        send(cPacket);
                        return cPacket;
                    }
                })
                .subscribe(new Observer<DatagramPacket>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        sDisposable = d;
                    }

                    @Override
                    public void onNext(DatagramPacket datagramPacket) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //开启接收
        startUDPReceive();
    }

    public void startUDPReceive() {
        Observable.interval(1000, 10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Long, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(Long aLong) throws Exception {
                        // 监听来信
                        hostList.clear();
                        byte[] data = new byte[1024];
                        DatagramPacket rp = new DatagramPacket(data, data.length);
                        mSocket.receive(rp);
                        return rp;
                    }
                })
                .filter(new Predicate<DatagramPacket>() {
                    @Override
                    public boolean test(DatagramPacket recePack) throws Exception {

                        if (recePack == null) {
                            return false;
                        }

                        if (recePack.getLength() <= 0) {
                            return false;
                        }

                        String data = new String(recePack.getData(), 0, recePack.getLength());

                        if (TextUtils.isEmpty(data)) {
                            return false;
                        }

                        if (recePack.getAddress().getHostAddress().equals(NetUtils.getIP(Utils.context))) {
                            if (data.equals(Consts.stopReceive)) {
                                stopKeepHeart();
                            }
                            return false;
                        }

                        return true;
                    }
                })
                .map(new Function<DatagramPacket, List<String>>() {
                    @Override
                    public List<String> apply(DatagramPacket datagramPacket) throws Exception {
                        hostList.add(datagramPacket.getAddress().getHostAddress());
                        return hostList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        fDisposable = d;
                    }

                    @Override
                    public void onNext(List<String> list) {
                        Log.i(TAG, "onNext: " + list.toString());
                        if (!list.isEmpty()) {
                            String ip = list.get(0);
                            try {
                                TcpService.getInstance().connect(ip, Consts.SearchPort);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void stopKeepHeart() {
        if (mSocket == null) {
            return;
        }

        if (mSocket.isClosed()) {
            return;
        }

        if (sDisposable != null) {
            sDisposable.dispose();
        }

        if (fDisposable != null) {
            fDisposable.dispose();
        }

        mSocket.close();

    }


    public Observable<Boolean> sentMessage(final String json, final InetAddress address, final boolean isLocal) {
        return Observable.just(json)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !TextUtils.isEmpty(json);
                    }
                })
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String s) throws Exception {
                        byte[] sendDate = s.getBytes();
                        DatagramPacket sendDP = new DatagramPacket(sendDate, sendDate.length, isLocal ? InetAddress.getByName("localhost") : address, Consts.SearchPort);
                        return send(sendDP);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private boolean send(DatagramPacket sendDP) throws IOException {
        if (mSocket == null) {
            return false;
        }

        if (mSocket.isClosed()) {
            return false;
        }

        if (mSocket.isConnected()) {
            return false;
        }
        mSocket.send(sendDP);
        return true;
    }

    public void sentStopMessage() {
        sentMessage(Consts.stopReceive, null, true).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.i(TAG, "onNext: " + aBoolean);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void startClient() {
        startUDPReceive();
    }


}
