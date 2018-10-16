package com.asa.meta.basehabit.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.asa.meta.helpers.toast.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;


public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    public Context mContext;

    public String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = getClass().getSimpleName();
        initViewDataBinding(savedInstanceState);
        registerContextChange();
        initView();
        initViewObservable();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(viewModel);
        binding.unbind();
    }

    public abstract int initContentView(Bundle savedInstanceState);

    public abstract int initVariableId();

    public abstract VM initViewModel();

    @Override
    public void initView() {

    }

    private void initViewDataBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        binding.setVariable(initVariableId(), viewModel = initViewModel());
        binding.setLifecycleOwner(this);
        getLifecycle().addObserver(viewModel);
    }


    @Override
    public void initViewObservable() {

    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }

    @Override
    public void initData() {
        viewModel.initData();
    }


    private final void registerContextChange() {
        viewModel.ucLiveData.finishActivityLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });

        viewModel.ucLiveData.showToastLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showToast(s);
            }
        });

        viewModel.ucLiveData.startActivityLiveData.observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = null;

                if (params.containsKey(BaseViewModel.ParameterField.BUNDLE)) {
                    bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                }

                startActivity(clz, bundle);
            }
        });

        viewModel.ucLiveData.startServiceLiveData.observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = null;

                if (params.containsKey(BaseViewModel.ParameterField.BUNDLE)) {
                    bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                }

                startService(clz, bundle);
            }
        });


        viewModel.ucLiveData.showDialog.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String TAG) {
                showDialog(TAG);
            }
        });

        viewModel.ucLiveData.hideDialog.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String TAG) {
                hideDialog(TAG);
            }
        });
    }

    public void showDialog(String TAG) {

    }

    public void hideDialog(String TAG) {

    }

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startService(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startService(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewModel != null) {
            viewModel.onActivityResult(requestCode, resultCode, data);
        }
    }
}
