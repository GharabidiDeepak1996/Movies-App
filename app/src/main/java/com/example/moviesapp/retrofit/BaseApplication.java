package com.example.moviesapp.retrofit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApplication extends Application {
//http://api.themoviedb.org/3/movie/popular?api_key=d4e5eaaee1c42ff0d65c9bca90ec6e4a
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
