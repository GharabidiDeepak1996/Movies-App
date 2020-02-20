package com.example.moviesapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.MovieEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.moviesapp.constant.Appconstant.MOVIEAPP_DATABASE_FILE;


@Database(entities= {MovieEntity.class, FavoriteEntity.class},version =1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

  public abstract MovieDao mDao();

  private static MovieDatabase instance;

  private static final int NUMBER_OF_THREADS = 4;
  public  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

  public static MovieDatabase getDatabaseInstance(Context context) {
    if (instance == null) {

      instance = Room.databaseBuilder( context.getApplicationContext(), MovieDatabase.class,MOVIEAPP_DATABASE_FILE  )
              .allowMainThreadQueries()
              .addCallback(sRoomDatabaseCallback)
              .build();
    }
    return instance;
  }
  private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);

      // If you want to keep data through app restarts,
      // comment out the following block
      databaseWriteExecutor.execute(() -> {


      });
    }
  };
}
