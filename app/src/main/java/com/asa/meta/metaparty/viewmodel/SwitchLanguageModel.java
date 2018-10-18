package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.model.SwitchModel;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

public class SwitchLanguageModel extends BaseViewModel {
    public final ObservableList<SwitchModel.LanguageBean> items = new ObservableArrayList<>();
    public final ItemBinding<SwitchModel.LanguageBean> itemBinding = ItemBinding.of(BR.items, R.layout.item_switch_language);
    public MergeObservableList<Object> data = new MergeObservableList<>().insertItem("heard").insertList(items).insertItem("foot");
    public MutableLiveData<Integer> switchLanguage = new MutableLiveData<>();
    public OnSwitchLanguageListenner listenner = new OnSwitchLanguageListenner() {
        @Override
        public void onClick(SwitchModel.LanguageBean content) {
            showToast("类型：" + content.type + " ,内容" + content.language);
            switchLanguage.setValue(content.type);
        }
    };
    public final OnItemBind<Object> onItemBinding = new OnItemBind<Object>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, Object item) {
            if (String.class.equals(item.getClass())) {
                itemBinding.set(BR.items, R.layout.item_language_header);
            } else if (SwitchModel.LanguageBean.class.equals(item.getClass())) {
                itemBinding.set(BR.items, R.layout.item_switch_language).bindExtra(BR.listenner, listenner);
            }

        }
    };
    SwitchModel model = new SwitchModel();

    public SwitchLanguageModel(@NonNull Application application) {
        super(application);
    }

    public void addItem() {
        List<SwitchModel.LanguageBean> languageBeans = model.getSwitchData();
        items.addAll(languageBeans);
    }

    public interface OnSwitchLanguageListenner {
        void onClick(SwitchModel.LanguageBean content);
    }


}
