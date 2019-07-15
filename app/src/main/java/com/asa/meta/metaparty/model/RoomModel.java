package com.asa.meta.metaparty.model;

import androidx.lifecycle.LiveData;

import com.asa.meta.metaparty.data.DaoService;
import com.asa.meta.metaparty.data.bean.Book;

import java.util.List;

public class RoomModel {
    public LiveData<List<Book>> getAllBook() {
        return DaoService.getInstance().getAllBook();
    }

    public void addBook(Book... books) {
        DaoService.getInstance().addBook(books);
    }
}
