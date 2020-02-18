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
import com.example.moviesapp.model.FavoriteEntity;

import java.util.List;

public class FavAdapter extends BaseAdapter {
    private static final String TAG = "FavAdapter";
    Context mcontext;
    List<FavoriteEntity> fav;
    LayoutInflater inflter;
    public FavAdapter(Context context, List<FavoriteEntity> mfav) {
        mcontext=context;
        fav=mfav;
        inflter = (LayoutInflater.from(context));
    }

    public void setCollection(List<FavoriteEntity> FavoriteCollection) {
fav=FavoriteCollection;
            notifyDataSetChanged();

    }
    public void clearCollection() {
        fav.clear();
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: "+fav.size());
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
        final FavoriteEntity mfav = fav.get(position);
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
