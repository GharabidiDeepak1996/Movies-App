package com.example.moviesapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.model.MovieEntity;
import com.example.moviesapp.model.MoviesResponse;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.retrofit.BaseApplication;
import com.example.moviesapp.retrofit.MovieAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirstFragmentViewModel extends AndroidViewModel {

    private static final String TAG = "FirstFragmentViewModel";


    MutableLiveData<MoviesResponse> moviesResponseMutableLiveData = new MutableLiveData<>();

    MutableLiveData<String> errorResponseLiveData = new MutableLiveData<>();

    MutableLiveData<List<MovieEntity>> dbMoviesLiveData = new MutableLiveData<>();

    Context context;

    public FirstFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }
//response
    public MutableLiveData<MoviesResponse> getMoviesLiveData() {
        return moviesResponseMutableLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorResponseLiveData;
    }

    public MutableLiveData<List<MovieEntity>> getDbMoviesLiveData() {
        return dbMoviesLiveData;
    }

    public void loadData(int page) {
        //https://proandroiddev.com/building-an-android-app-using-android-architecture-components-room-viewmodel-and-livedata-702a0af899ae
        // https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
        //Instanceretrofit
        Retrofit retrofit = BaseApplication.getRetrofitInstance();
        //interface
        MovieAPI api = retrofit.create(MovieAPI.class);
        api.getPopularMovies(Appconstant.THE_MOVIE_DB_API_TOKEN, page).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.message());
                    MoviesResponse moviesResponse = response.body();
                    //response
                    moviesResponseMutableLiveData.setValue(moviesResponse);
                    addMoviesInDB(moviesResponse);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: unsucessfull");
                errorResponseLiveData.setValue(t.getMessage());
            }
        });

    }

    void addMoviesInDB(MoviesResponse moviesResponse) {
        if (moviesResponse != null && moviesResponse.getResults() != null) {
            MovieDatabase database = MovieDatabase.getDatabaseInstance(context);
            MovieDao dao = database.mDao();
            dao.insertAll(moviesResponse.getResults());
        }
    }

    public void loadMoviesFromDB() {
        MovieDatabase database = MovieDatabase.getDatabaseInstance(context);
        MovieDao dao = database.mDao();
        List<MovieEntity> movieEntityList = dao.getAllMovies();
        dbMoviesLiveData.setValue(movieEntityList);
    }

}
