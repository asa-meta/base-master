package com.asa.meta.metaparty.socket;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
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

public class DeviceSearchService implements DeviceSearch {
    private final int MaxNumber = 200;

    private DatagramSocket socket = null;
    private InetAddress allAddress = null;

    private byte[] sendData = Consts.heartSendKeep.getBytes();

    private Disposable searchDisposable = null;
    private Disposable feedbackDisposable = null;

    private DatagramPacket sendAllPacket = null;
    private List<String> deviceHostList = new ArrayList<>();

    private String TAG = "服务测试";
    private boolean isSearch;//是否搜索
    private SearchCallback searchCallback;

    public DeviceSearchService(SearchCallback searchCallback) throws SocketException, UnknownHostException {
        this.searchCallback = searchCallback;
        this.socket = new DatagramSocket();
        this.allAddress = InetAddress.getByName(Consts.localIP);
        this.sendAllPacket = new DatagramPacket(sendData, sendData.length, allAddress, Consts.SearchPort);
        this.sendAllPacket.setData(sendData);

    }

    @Override
    public void startFeedback() {
        Observable.interval(1000, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Long, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(Long aLong) throws Exception {
                        // 监听来信
                        byte[] receData = new byte[1024];
                        DatagramPacket recePack = new DatagramPacket(receData, receData.length);
                        Log.i(TAG, " \n----------------------接收反馈 start ---------------------------");
                        socket.receive(recePack);
                        Log.i(TAG, " \n----------------------接收反馈 end ---------------------------");
                        return recePack;
                    }
                })
                .filter(new Predicate<DatagramPacket>() {
                    @Override
                    public boolean test(DatagramPacket recePack) throws Exception {
                        return recePack.getLength() > 0;
                    }
                })
                .filter(new Predicate<DatagramPacket>() {
                    @Override
                    public boolean test(DatagramPacket recePack) throws Exception {
                        String data = new String(recePack.getData(), 0, recePack.getLength());
                        return !TextUtils.isEmpty(data) && data.equals(Consts.heartConfirmKeep);
                    }
                })
                .map(new Function<DatagramPacket, List<String>>() {
                    @Override
                    public List<String> apply(DatagramPacket datagramPacket) throws Exception {
                        deviceHostList.add(datagramPacket.getAddress().getHostAddress());
                        return deviceHostList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        feedbackDisposable = d;
                    }

                    @Override
                    public void onNext(List<String> list) {
                        Log.i(TAG, " \n----------------------反馈的地址 ---------------------------" + list.toString());
                        searchCallback.onGetList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        searchCallback.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        searchCallback.onComplete();
                    }
                });
        ;
    }

    @Override
    public void startSearch() throws IOException {
        isSearch = true;
        searchDisposable = Observable.interval(3000, 10 * 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .map(new Function<Long, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(Long aLong) throws Exception {

                        deviceHostList.clear();
                        Log.i(TAG, " \n----------------------搜索发送 start ---------------------------" + Thread.currentThread().getName());
                        socket.send(sendAllPacket);
                        Log.i(TAG, " \n----------------------搜索发送 end ---------------------------");
                        return sendAllPacket;
                    }
                }).subscribe(new Consumer<DatagramPacket>() {
                    @Override
                    public void accept(DatagramPacket datagramPacket) throws Exception {

                    }
                });

    }

    @Override
    public void stopSearch() {
        isSearch = false;
        if (searchDisposable != null) {
            searchDisposable.dispose();
        }
        closeSocket();

    }

    private void closeSocket() {
        if (socket != null) {
            socket.close();
            Log.i(TAG, "closeSocket ");
            socket = null;
        }
    }

    @Override
    public void stopReceive() throws UnknownHostException {
        Observable.create(new ObservableOnSubscribe<DatagramPacket>() {
            @Override
            public void subscribe(ObservableEmitter<DatagramPacket> e) {
                byte[] sendDate = Consts.stopReceive.getBytes();
                try {
                    DatagramPacket sendDP = new DatagramPacket(sendDate, sendDate.length, InetAddress.getByName("localhost"), Consts.SearchPort);
                    Log.i(TAG, "\n----------------------发送取消接收的信息 start ---------------------------");
                    socket.send(sendDP);
                    Log.i(TAG, "\n----------------------发送取消接收的信息 start ---------------------------");

                    e.onNext(sendDP);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                    e.onError(e1);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    e.onError(e1);
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DatagramPacket>() {
            @Override
            public void onSubscribe(Disposable d) {

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

    }

    public interface SearchCallback {
        void onComplete();

        void onGetList(List<String> list);

        void onError(String error);
    }


}
