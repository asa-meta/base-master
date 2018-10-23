package com.asa.meta.metaparty.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.asa.meta.metaparty.data.bean.Book;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertBook(Book... books);

    @Update
    public void updateBook(Book... books);

    @Delete
    public void deleteBook(Book... books);

    @Query("SELECT * FROM book")
    public LiveData<List<Book>> queryAllBook();
}
