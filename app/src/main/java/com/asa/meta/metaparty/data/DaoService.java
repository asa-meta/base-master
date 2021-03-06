package com.asa.meta.metaparty.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.asa.meta.metaparty.application.BaseApplication;
import com.asa.meta.metaparty.data.bean.Book;
import com.asa.meta.metaparty.data.dao.BookDao;
import com.asa.meta.metaparty.data.dataBase.AppDataBase;

import java.util.List;

public class DaoService {
    public static DaoService daoService;

    private AppDataBase appDataBase;

    private DaoService() {
        appDataBase = Room.databaseBuilder(BaseApplication.getInstance(), AppDataBase.class, "asa.db").addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        }).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static DaoService getInstance() {
        if (daoService == null) {
            synchronized (DaoService.class) {
                if (daoService == null) {
                    daoService = new DaoService();
                }
            }
        }
        return daoService;
    }


    public BookDao getBookDao() {
        return appDataBase.bookDao();
    }

    public LiveData<List<Book>> getAllBook() {
        return getBookDao().queryAllBook();
    }

    public void addBook(Book... books) {
        getBookDao().insertBook(books);
    }
}
