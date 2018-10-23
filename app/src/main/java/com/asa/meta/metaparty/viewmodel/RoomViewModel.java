package com.asa.meta.metaparty.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.asa.meta.basehabit.base.BaseViewModel;
import com.asa.meta.metaparty.BR;
import com.asa.meta.metaparty.R;
import com.asa.meta.metaparty.data.bean.Book;
import com.asa.meta.metaparty.model.RoomModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class RoomViewModel extends BaseViewModel {
    public final ItemBinding<Book> itemBinding = ItemBinding.of(BR.items, R.layout.item_room_book);
    public LiveData<List<Book>> items;
    private RoomModel roomModel;

    public RoomViewModel(@NonNull Application application) {
        super(application);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        roomModel = new RoomModel();

        Observable.create((ObservableOnSubscribe<LiveData<List<Book>>>) emitter -> {
            items = roomModel.getAllBook();
            emitter.onNext(items);
        }).subscribe(listLiveData -> {
            Log.i(TAG, "onCreate: 更新");
        });


        Observable.interval(0, 1000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            Book book = new Book();
            book.like = 100;
            book.bookName = "我变变变" + aLong;
            book.author = "asa";
            book.id = aLong;

            roomModel.addBook(book);
        });


    }
}
