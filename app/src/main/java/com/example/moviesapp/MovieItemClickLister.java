package com.example.moviesapp;

import android.graphics.Movie;
import android.widget.ImageView;

public interface MovieItemClickLister {

    void onMovieClick(Movie movie, ImageView movieimageView);//we will need the imageview  to make the shared animation
}
