package com.example.moviesapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieEntity;

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
    MovieEntity movieEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

//received from firstfragment
        movieEntity = (MovieEntity) getIntent().getSerializableExtra("movies");
        Log.d(TAG, "onCreate: " + movieEntity.getMovieid());

        String movieposter = movieEntity.getPosterPath();
        String poster = "https://image.tmdb.org/t/p/w500" + movieposter;

        Glide.with(this)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(imageView);

        listTextView.get(0).setText(movieEntity.getOriginalTitle());
        listTextView.get(1).setText(movieEntity.getReleaseDate());
        listTextView.get(2).setText(String.valueOf(movieEntity.getVoteAverage()));
        listTextView.get(3).setText(movieEntity.getOriginalLanguage());
        listTextView.get(4).setText(movieEntity.getOverview());
    }
}
