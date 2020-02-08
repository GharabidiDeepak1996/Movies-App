package com.example.moviesapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favorite")
public class Favorites {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "link")
    public String link;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "mennuId")
    public String menuId;


}
