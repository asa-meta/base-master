package com.asa.meta.basehabit.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;

import com.asa.meta.helpers.toast.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    public String TAG = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

    public static <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }

        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        binding.setVariable(initVariableId(), viewModel = initViewModel());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerContextChange();
        initData();

        initViewObservable();

        viewModel.onCreate();

        viewModel.registerRxBus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.removeRxBus();
        viewModel.onDestroy();

        viewModel = null;
        binding.unbind();
    }


    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }

    public abstract NavController getNavController();

    public void navigate(int resId, Bundle bundle) {
        if (getNavController() != null) {
            getNavController().navigate(resId, bundle);
        }
    }

    private final void registerContextChange() {

        viewModel.ucLiveData.showToastLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showToast(s);
            }
        });

        viewModel.ucLiveData.startFragmentByActionId.observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> params) {
                Bundle bundle = null;
                int resId = (int) params.get(BaseViewModel.ParameterField.ACTION_ID);

                if (params.containsKey(BaseViewModel.ParameterField.BUNDLE)) {
                    bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                }

                navigate(resId, bundle);
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
    }

    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public abstract VM initViewModel();

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    public boolean onBackPressed() {
        return false;
    }
}
