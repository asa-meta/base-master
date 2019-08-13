package com.asa.meta.metaparty.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.asa.meta.basehabit.BR;
import com.asa.meta.basehabit.base.BaseFragment;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.FragmentApiBinding;
import com.asa.meta.metaparty.viewmodel.ApiFragmentViewModel;

public class ApiFragment extends BaseFragment<FragmentApiBinding, ApiFragmentViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_api;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ApiFragmentViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public void initView() {

    }
}
