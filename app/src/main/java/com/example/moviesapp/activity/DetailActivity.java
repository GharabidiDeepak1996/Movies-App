package com.example.moviesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    @BindViews({R.id.movietitle, R.id.TReleasedate, R.id.TRating, R.id.ToriginalLanguage, R.id.Toverview})
    List<TextView> listTextView;
    @BindView(R.id.movieimage)
    ImageView imageView;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);


        movie = (Movie) getIntent().getSerializableExtra("movies");
        Log.d(TAG, "onCreate: " + movie.getMovieid());

        String movieposter = movie.getPosterPath();
        String poster = "https://image.tmdb.org/t/p/w500" + movieposter;

        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(imageView);

        listTextView.get(0).setText(movie.getOriginalTitle());
        listTextView.get(1).setText(movie.getReleaseDate());
        listTextView.get(2).setText(String.valueOf(movie.getVoteAverage()));
        listTextView.get(3).setText(movie.getOriginalLanguage());
        listTextView.get(4).setText(movie.getOverview());
    }
}
