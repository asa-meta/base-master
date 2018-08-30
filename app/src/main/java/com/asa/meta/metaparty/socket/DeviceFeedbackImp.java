package com.asa.meta.metaparty.socket;

import android.text.TextUtils;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class DeviceFeedbackImp implements DeviceFeedbackService {
    byte[] feedbackData = Consts.heartConfirmKeep.getBytes();
    private DatagramSocket socket = null;
    private Disposable disposable = null;
    private DatagramPacket packet = null;
    private String TAG = "服务测试";

    @Override
    public void startFeedback() throws SocketException {
        Observable.interval(0, 10 * 1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread())
                .map(new Function<Long, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(Long aLong) throws Exception {
                        Log.i(TAG, " ---------------------反馈接受 start ---------------------------");
                        if (socket == null || socket.isClosed()) {
                            if (socket != null && !socket.isClosed()) {
                                socket.close();
                            }
                            socket = new DatagramSocket(null);
                            socket.setReuseAddress(true);
                            socket.bind(new InetSocketAddress(Consts.SearchPort));
                        }
                        byte[] data = new byte[1024];
                        packet = new DatagramPacket(data, data.length);
                        socket.receive(packet);
                        Log.i(TAG, " ---------------------反馈接受 end ---------------------------");
                        return packet;
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
                        Log.i(TAG, " ---------------------反馈接受数据  ---------------------------" + data);

                        return !TextUtils.isEmpty(data) && data.equals(Consts.heartSendKeep);
                    }
                })
                .map(new Function<DatagramPacket, DatagramPacket>() {
                    @Override
                    public DatagramPacket apply(DatagramPacket datagramPacket) throws Exception {
                        DatagramPacket feedBackPt = new DatagramPacket(feedbackData, feedbackData.length, datagramPacket.getSocketAddress());
                        Log.i(TAG, " ---------------------反馈回复 start " + datagramPacket.getSocketAddress().toString() + " ---------------------------");
                        socket.send(feedBackPt);
                        Log.i(TAG, " ---------------------反馈回复 end ---------------------------");
                        return feedBackPt;
                    }
                }).subscribe(new Observer<DatagramPacket>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(DatagramPacket datagramPacket) {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete:");
            }
        });
    }

    @Override
    public void stopFeedback() {

    }
}
