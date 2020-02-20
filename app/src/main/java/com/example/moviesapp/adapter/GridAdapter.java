package com.example.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;

import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.MovieEntity;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.viewmodel.MovieViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.io.Serializable;
import java.util.List;

import static com.example.moviesapp.view.fragment.SecondFragment.THIS_BROADCAST_FOR_ADD_FAVORITE_ITEMS;


public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
    private List<MovieEntity> movieEntityList;
    private Context mcontext;
    private LayoutInflater inflter;
    private ShineButton shineButton;
    private MovieViewModel movieViewModel;
    MovieDao mdao;

    public GridAdapter(Context context, List<MovieEntity> movieEntities) {
        movieEntityList = movieEntities;
        mcontext = context;
        inflter = (LayoutInflater.from(context));
        MovieDatabase database = MovieDatabase.getDatabaseInstance(mcontext);
        mdao = database.mDao();
        movieViewModel = ViewModelProviders.of((FragmentActivity) context).get(MovieViewModel.class);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: " + movieEntityList.size());
        return movieEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clearCollection() {
        movieEntityList.clear();
    }

    public List<MovieEntity> getCollection() {
        return movieEntityList;
    }

    public void setCollection(List<MovieEntity> chatCollection) {
        if (chatCollection != null) {
            movieEntityList.addAll(chatCollection);
            notifyDataSetChanged();
        }
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final MovieEntity movieEntity = movieEntityList.get(position);

        view = inflter.inflate(R.layout.card_view, null); // inflate the layout
        ImageView image = view.findViewById(R.id.movieimage);
        TextView title = view.findViewById(R.id.movietitle);
        TextView rating = view.findViewById(R.id.movierating);


        title.setText(movieEntity.getOriginalTitle());
        String vote = Double.toString(movieEntity.getVoteAverage());
        rating.setText(vote);

        //https://www.learn2crack.com/2014/01/android-custom-gridview.html

        String poster = "https://image.tmdb.org/t/p/w500" + movieEntity.getPosterPath();

        Glide.with(mcontext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(image);

        //https://github.com/ChadCSong/ShineButton
        shineButton = (ShineButton) view.findViewById(R.id.fav);
        shineButton.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean favorite) {
                if (favorite == true) {
                    FavoriteEntity fav = new FavoriteEntity(movieEntity.getMovieid(), movieEntity.getOriginalTitle(), movieEntity.getVoteAverage(), movieEntity.getPosterPath(), movieEntity.getOverview(), favorite);


                    //to second fragment
                    movieViewModel.insert(fav);
                    movieViewModel.getAllData().observe((LifecycleOwner) mcontext, new Observer<List<FavoriteEntity>>() {
                        @Override
                        public void onChanged(List<FavoriteEntity> favoriteEntities) {
                            Intent intent = new Intent(THIS_BROADCAST_FOR_ADD_FAVORITE_ITEMS);
                            intent.putExtra("favoriteitems", (Serializable) favoriteEntities);
                            mcontext.sendBroadcast(intent);
                        }
                    });
                    Toast.makeText(mcontext, "FavoriteItem Added", Toast.LENGTH_LONG).show();
                } else {

                    Log.d(TAG, "onFavoriteChanged: " + movieEntity.getMovieid());

                    movieViewModel.delete(movieEntity.getMovieid());
                    movieViewModel.getAllData().observe((LifecycleOwner) mcontext, new Observer<List<FavoriteEntity>>() {
                        @Override
                        public void onChanged(List<FavoriteEntity> favoriteEntities) {
                            Intent intent = new Intent(THIS_BROADCAST_FOR_ADD_FAVORITE_ITEMS);
                            intent.putExtra("favoriteitems", (Serializable) favoriteEntities);
                            mcontext.sendBroadcast(intent);
                            shineButton.setChecked(false);
                        }
                    });

                    Toast.makeText(mcontext, "FavoriteItem Deleted", Toast.LENGTH_LONG).show();
                }
            }
        });

        Checkstatus(movieEntity.getMovieid());

        return view;
    }

    //to check the status
    private void Checkstatus(int movieId) {

        List<FavoriteEntity> check = mdao.readSelectedFavoriteItem(movieId);
        Log.d(TAG, "Checkstatus: " + check.size());


        for (int i = 0; i < check.size(); i++) {
            int movieid = check.get(i).getMovieid();
            Log.d(TAG, "getView: " + check.size());

            if (movieId == movieid) {
                shineButton.setChecked(true);
            }
        }

    }

}
