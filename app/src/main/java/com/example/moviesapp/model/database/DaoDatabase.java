package com.example.moviesapp.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.moviesapp.model.FavoriteEntry;
import com.example.moviesapp.model.MovieEntity;

import java.util.List;


@Dao
public interface DaoDatabase {
//https://developer.android.com/training/data-storage/room


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity movieEntity);

    @Query("SELECT * FROM MovieEntity WHERE originalTitle LIKE :searchquery" )
    List<MovieEntity> search(String searchquery);

    //fetch data of fav
    @Query("SELECT * FROM favoritetable")
    List<FavoriteEntry> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntry favoriteEntry);

    @Query("DELETE FROM favoritetable WHERE movieid = :movie_id")
    void deleteFavoriteWithId(int movie_id);

    //@Query("SELECT * FROM hamster WHERE name LIKE '%:arg0%'")
}
