package com.example.moviesapp.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.FavAdapter;
import com.example.moviesapp.model.FavoriteEntry;
import com.example.moviesapp.model.database.DaoDatabase;
import com.example.moviesapp.model.database.MainDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SecondFragment extends Fragment {
    private static final String TAG = "SecondFragment";
    @BindView(R.id.fav_gird_view)
    GridView gridView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, view);
        MainDatabase database = MainDatabase.getDatabaseInstance(getActivity());
        DaoDatabase data = database.mDao();
        List<FavoriteEntry> mfav=data.loadAll();
        Log.d(TAG, "onCreateView: "+mfav.size());

        FavAdapter fav=new FavAdapter(getActivity(),mfav);
        gridView.setAdapter(fav);

        return view;
    }

}
