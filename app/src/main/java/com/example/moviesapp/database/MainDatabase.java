package com.example.moviesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesapp.model.FavoriteEntry;
import com.example.moviesapp.model.Movie;


@Database(entities= {Movie.class, FavoriteEntry.class},version =1,exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

  public abstract DaoDatabase mDao();

  private static MainDatabase instance;
  public static MainDatabase getDatabaseInstance(Context context) {
    if (instance == null) {

      instance = Room.databaseBuilder( context.getApplicationContext(), MainDatabase.class,"Movieapp_Database_File"  )
              .allowMainThreadQueries()
              .build();
    }
    return instance;
  }
}
