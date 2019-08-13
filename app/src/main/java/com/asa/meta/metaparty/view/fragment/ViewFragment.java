package com.asa.meta.metaparty.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.asa.meta.basehabit.BR;
import com.asa.meta.basehabit.base.BaseFragment;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.FragmentViewBinding;
import com.asa.meta.metaparty.viewmodel.ViewFragmentViewModel;

public class ViewFragment extends BaseFragment<FragmentViewBinding, ViewFragmentViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_view;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ViewFragmentViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public void initView() {

    }
}
