package com.example.moviesapp.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.MovieEntity;

import static com.example.moviesapp.constant.Appconstant.MOVIEAPP_DATABASE_FILE;


@Database(entities= {MovieEntity.class, FavoriteEntity.class},version =1,exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

  public abstract DaoDatabase mDao();

  private static MainDatabase instance;
  public static MainDatabase getDatabaseInstance(Context context) {
    if (instance == null) {

      instance = Room.databaseBuilder( context.getApplicationContext(), MainDatabase.class,MOVIEAPP_DATABASE_FILE  )
              .allowMainThreadQueries()
              .build();
    }
    return instance;
  }
}
