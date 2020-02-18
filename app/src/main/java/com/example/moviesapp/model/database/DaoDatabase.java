package com.example.moviesapp.model.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.MovieEntity;

import java.util.List;


@Dao
public interface DaoDatabase {
//https://developer.android.com/training/data-storage/room

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieEntity> movieEntities);

    @Query("SELECT * FROM MovieEntity WHERE originalTitle LIKE :searchquery" )
    List<MovieEntity> search(String searchquery);

    @Query("SELECT * FROM MovieEntity")
    List<MovieEntity> getAllMovies();

//fetch data of fav
    @Query("SELECT * FROM favoritetable")
    List<FavoriteEntity> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntity favoriteEntity);

    @Query("SELECT * FROM favoritetable WHERE value")
    List<FavoriteEntity> values();

    @Query("DELETE FROM favoritetable WHERE movieid = :movie_id")
    void deleteFavoriteWithId(int movie_id);

    @Query("SELECT * FROM favoritetable WHERE movieid = :movie_id")
    List<FavoriteEntity> readAllData(int movie_id);

}
