package com.asa.meta.metaparty.data.dataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.asa.meta.metaparty.data.bean.Book;
import com.asa.meta.metaparty.data.dao.BookDao;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BookDao bookDao();
}
