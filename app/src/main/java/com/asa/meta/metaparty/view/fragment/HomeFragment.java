package com.asa.meta.metaparty.view.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.asa.meta.basehabit.BR;
import com.asa.meta.basehabit.base.BaseFragment;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.FragmentHomeBinding;
import com.asa.meta.metaparty.viewmodel.HomeFragmentViewModel;

import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.SimpleRxGalleryFinal;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public HomeFragmentViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public void initView() {
        initGallery();
    }

    @Override
    public void initData() {
        super.initData();
    }

    private void initGallery() {
        RxGalleryListener.getInstance().setRadioImageCheckedListener(new IRadioImageCheckedListener() {
            @Override
            public void cropAfter(Object t) {
                viewModel.setPhonePath(t.toString());
                Log.i(TAG, "cropAfter: " + t.toString());
            }

            @Override
            public boolean isActivityFinish() {
                return false;
            }
        });

        RxGalleryListener
                .getInstance()
                .setMultiImageCheckedListener(
                        new IMultiImageCheckedListener() {
                            @Override
                            public void selectedImg(Object t, boolean isChecked) {
                                viewModel.showToast(isChecked ? "选中" : "取消选中");
                            }

                            @Override
                            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                                viewModel.showToast("你最多只能选择" + maxSize + "张图片");
                            }
                        });
    }

    private void openGallery() {
        RxGalleryFinalApi.getInstance(getActivity()).openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
            @Override
            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                Log.i(TAG, "onEvent: " + imageRadioResultEvent.getResult().getOriginalPath());
            }
        });


    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        viewModel.openGallery.observe(this, aBoolean -> openGallery());

        viewModel.openCamera.observe(this, aBoolean -> SimpleRxGalleryFinal.get().init(new SimpleRxGalleryFinal.RxGalleryFinalCropListener() {

            @Override
            public Activity getSimpleActivity() {
                return getActivity();
            }

            @Override
            public void onCropCancel() {
                Log.i(TAG, "onCropCancel: ");
            }

            @Override
            public void onCropSuccess(@Nullable Uri uri) {
                Log.i(TAG, "onCropSuccess: " + uri.getPath());
                viewModel.setPhonePath(uri.getPath());
            }

            @Override
            public void onCropError(String errorMessage) {
                Log.i(TAG, "onCropError: " + errorMessage);
                viewModel.showToast(errorMessage);
            }
        }).openCamera());
    }
}
