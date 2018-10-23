package com.asa.meta.metaparty.data.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "book")
public class Book {
    @PrimaryKey
    public Long id;

    @ColumnInfo(name = "number")
    public int number;

    @ColumnInfo(name = "bookName")
    public String bookName;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "like")
    public int like;

    @ColumnInfo(name = "introduction")
    public String introduction;
}
