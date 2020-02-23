package com.example.moviesapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.MovieEntity;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private static final String TAG = "MovieViewModel";
    //https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#7
    //https://www.journaldev.com/21168/android-livedata
int Movieid ;
    private MovieDao movieDao;
    private LiveData<List<FavoriteEntity>> getAllFavoriteData;


    public MovieViewModel(@NonNull Application application) {
        super(application);

        MovieDatabase database = MovieDatabase.getDatabaseInstance(application);
        movieDao=database.mDao();
        getAllFavoriteData=movieDao.loadAll();

    }

    public LiveData<List<FavoriteEntity>> getAllData() {

        return getAllFavoriteData;
    }


    public void insert(FavoriteEntity data) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.insertFavorite(data);
        });
    }
    public void delete(int movieid) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
           movieDao.deleteFavoriteWithId(movieid);
        });
    }


}
