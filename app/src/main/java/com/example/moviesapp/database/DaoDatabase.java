package com.example.moviesapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.moviesapp.model.FavoriteEntry;
import com.example.moviesapp.model.Movie;

import java.util.List;


@Dao
public interface DaoDatabase {
//https://developer.android.com/training/data-storage/room
    @Insert
    void insertAll(Movie movie);

    @Query("SELECT * FROM Movie WHERE originalTitle LIKE :searchquery" )
    List<Movie> search(String searchquery);

//fetch data of fav
    @Query("SELECT * FROM favoritetable WHERE title = :title")
    List<FavoriteEntry> loadAll(String title);

    @Insert
    void insertFavorite(FavoriteEntry favoriteEntry);
    @Query("DELETE FROM favoritetable WHERE movieid = :movie_id")
    void deleteFavoriteWithId(int movie_id);

}
