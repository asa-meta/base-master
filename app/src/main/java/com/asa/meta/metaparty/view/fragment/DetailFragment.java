package com.asa.meta.metaparty.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.asa.meta.basehabit.BR;
import com.asa.meta.basehabit.base.BaseFragment;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.FragmentDetailBinding;
import com.asa.meta.metaparty.viewmodel.DetailFragmentViewModel;

public class DetailFragment extends BaseFragment<FragmentDetailBinding, DetailFragmentViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public DetailFragmentViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getArguments();

        Log.i(TAG, bundle.getString("XXXX", "沒有數據"));


    }

    @Override
    public NavController getNavController() {
        return Navigation.findNavController(getActivity(), R.id.fragment_container);
    }
}
