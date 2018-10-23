package com.asa.meta.metaparty.data.dataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.asa.meta.metaparty.data.bean.Book;
import com.asa.meta.metaparty.data.dao.BookDao;

@Database(entities = {Book.class}, version = 1, exportSchema = true)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BookDao bookDao();
}
