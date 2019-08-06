package com.asa.meta.metaparty.view.activity;

import android.os.Bundle;

import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.databinding.ActivityRoomBinding;
import com.asa.meta.metaparty.view.activity.base.ILogoutActivity;
import com.asa.meta.metaparty.viewmodel.RoomViewModel;

public class RoomActivity extends ILogoutActivity<ActivityRoomBinding, RoomViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_room;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
}
