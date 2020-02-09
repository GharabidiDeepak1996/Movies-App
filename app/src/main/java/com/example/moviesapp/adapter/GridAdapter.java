package com.example.moviesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieEntity;
import com.example.moviesapp.model.database.DaoDatabase;
import com.example.moviesapp.model.database.MainDatabase;
import com.example.moviesapp.model.FavoriteEntry;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
    List<MovieEntity> lmovies;
    Context mcontext;
    LayoutInflater inflter;
    List<FavoriteEntry> entries = new ArrayList<>();
    MainDatabase database;
    DaoDatabase data;
    String movietitile, movieposter, movieoverview;
    int movieid;
    double movierate;
    FavAdapter adapter;

    public GridAdapter(Context context, List<MovieEntity> movieEntities) {
        lmovies = movieEntities;
        mcontext = context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: " + lmovies.size());
        return lmovies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setCollection(List<MovieEntity> chatCollection) {
        if (chatCollection != null) {
            lmovies.addAll(chatCollection);
            notifyDataSetChanged();
        }


        Log.d(TAG, "setCollection: " + lmovies.size());
    }

    public void saveFavorite() {
        final FavoriteEntry favoriteEntry = new FavoriteEntry(movieid, movietitile, movierate, movieposter, movieoverview);
                data.insertFavorite(favoriteEntry);

    }
    public void clearCollection() {
        lmovies.clear();
    }
    private void deleteFavorite(final int movie_id) {
                data.deleteFavoriteWithId(movie_id);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final MovieEntity chat = lmovies.get(position);

        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setPosterPath(chat.getPosterPath());
        movieEntity.setOverview(chat.getOverview());
        movieEntity.setReleaseDate(chat.getReleaseDate());
        movieEntity.setOriginalTitle(chat.getOriginalTitle());
        movieEntity.setOriginalLanguage(chat.getOriginalLanguage());
        movieEntity.setVoteAverage(chat.getVoteAverage());
        movieEntity.setMovieid(chat.getMovieid());
        movieid = chat.getMovieid();
        movietitile = chat.getOriginalTitle();
        movierate = chat.getVoteAverage();
        movieposter = chat.getPosterPath();
        movieoverview = chat.getOverview();

        database = MainDatabase.getDatabaseInstance(mcontext);
        data = database.mDao();
        data.insertAll(movieEntity);

        view = inflter.inflate(R.layout.card_view, null); // inflate the layout
        ImageView image = view.findViewById(R.id.movieimage);
        TextView title = view.findViewById(R.id.movietitle);
        TextView rating = view.findViewById(R.id.movierating);
        MaterialFavoriteButton materialFavoriteButton = (MaterialFavoriteButton) view.findViewById(R.id.fav);
        Log.d(TAG, "getView: " + chat.getOriginalTitle());

        title.setText(chat.getOriginalTitle());
        String vote = Double.toString(chat.getVoteAverage());
        rating.setText(vote);

        //https://www.learn2crack.com/2014/01/android-custom-gridview.html

        String poster = "https://image.tmdb.org/t/p/w500" + chat.getPosterPath();

        Glide.with(mcontext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(image);

//https://android-arsenal.com/details/1/2612

        if (entries.size() > 0) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                deleteFavorite(movieid);
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                // int movie_id = getIntent().getExtras().getInt("id");
                                deleteFavorite(movieid);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        return view;
    }
}
