package com.example.moviesapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.retrofit.Movie;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private static final String TAG = "GridAdapter";
List<Movie> lmovies;
Context mcontext;
    LayoutInflater inflter;

    public GridAdapter(Context context, List<Movie> movies) {
        lmovies=movies;
        mcontext=context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Movie chat = lmovies.get( position );

        view = inflter.inflate(R.layout.card_view,null); // inflate the layout
        ImageView image=view.findViewById(R.id.thumbnail);
        TextView title=view.findViewById(R.id.title);
        TextView rating=view.findViewById(R.id.userrating);
        Log.d(TAG, "getView: "+chat.getOriginalTitle());

       title.setText(chat.getOriginalTitle());
        String vote = Double.toString(chat.getVoteAverage());
       rating.setText(vote);

       //https://www.learn2crack.com/2014/01/android-custom-gridview.html

        String poster = "https://image.tmdb.org/t/p/w500" + chat.getPosterPath();

        Glide.with(mcontext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(image);
        return view;
    }
}
