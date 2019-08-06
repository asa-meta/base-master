package com.asa.meta.metaparty.view.activity.base;

import android.util.Log;

import androidx.databinding.ViewDataBinding;

import com.asa.meta.basehabit.base.BaseActivity;
import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.metaparty.controller.LogoutController;

import java.util.Date;

public abstract class ILogoutActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.i(TAG, "onUserInteraction");
        LogoutController.getInstance().setLastTime(new Date().getTime());
    }
}
