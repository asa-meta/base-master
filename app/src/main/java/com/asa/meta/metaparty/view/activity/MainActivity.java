package com.asa.meta.metaparty.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.asa.meta.basehabit.bus.RxBus;
import com.asa.meta.helpers.app.AppManager;
import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.controller.NotifyController;
import com.asa.meta.metaparty.databinding.ActivityMainBinding;
import com.asa.meta.metaparty.event.Logout;
import com.asa.meta.metaparty.view.activity.base.ILogoutActivity;
import com.asa.meta.metaparty.viewmodel.MainViewModel;
import com.asa.meta.metaparty.worker.LogoutWorker;

public class MainActivity extends ILogoutActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public static void reStart(Context context) {
        AppManager.getAppManager().finishActivity();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void initData() {
        super.initData();
        LogoutWorker.start();
        RxBus.getDefault()
                .toObservable(Logout.class)
                .subscribe(logout -> NotifyController.notifyTest2(mContext, "警告", "你長時間未活動，已退出登錄"));
    }


    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.fragment_container).navigateUp();
    }

    @Override
    public void finish() {
        super.finish();
        LogoutWorker.finish();
    }

    @Override
    public void initView() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigtionView, navController);
    }


}
