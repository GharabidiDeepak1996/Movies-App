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
import com.example.moviesapp.model.FavoriteEntry;
import com.example.moviesapp.model.MovieEntity;

import java.util.Collection;
import java.util.List;

public class FavAdapter extends BaseAdapter {
    Context mcontext;
    List<FavoriteEntry> fav;
    LayoutInflater inflter;
    public FavAdapter(Context context, List<FavoriteEntry> mfav) {
        mcontext=context;
        fav=mfav;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return fav.size();
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
        final FavoriteEntry mfav = fav.get(position);
        view = inflter.inflate(R.layout.fav_card_view, null);
        ImageView image = view.findViewById(R.id.fmovieimage);
        TextView title = view.findViewById(R.id.fmovietitle);
        TextView rating = view.findViewById(R.id.fmovierating);

        String poster = "https://image.tmdb.org/t/p/w500" + mfav.getPosterpath();
        Glide.with(mcontext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(image);
        title.setText(mfav.getTitle());
        String vote = Double.toString(mfav.getUserrating());
        rating.setText(vote);
        return view;
    }
}
