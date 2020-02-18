package com.example.moviesapp.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.FavAdapter;
import com.example.moviesapp.model.FavoriteEntity;
import com.example.moviesapp.model.database.DaoDatabase;
import com.example.moviesapp.model.database.MainDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SecondFragment extends Fragment {
    private static final String TAG = "SecondFragment";
    @BindView(R.id.fav_gird_view)
    GridView gridView;
    FavAdapter fav;
    private List<FavoriteEntity> mfav;
    public static final String THIS_BROADCAST_FOR_ADD_FAVORITE_ITEMS = "favorite items";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter=new IntentFilter( THIS_BROADCAST_FOR_ADD_FAVORITE_ITEMS );
        getActivity().registerReceiver( broadcastforsearchbar,intentFilter );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, view);
        MainDatabase database = MainDatabase.getDatabaseInstance(getActivity());
        DaoDatabase data = database.mDao();
         mfav=data.loadAll();
        Log.d(TAG, "onCreateView: "+mfav.size());

         fav=new FavAdapter(getActivity(),mfav);
        gridView.setAdapter(fav);
        return view;
    }

    //receive from gridadapter.
    private BroadcastReceiver broadcastforsearchbar=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<FavoriteEntity> data = ( List<FavoriteEntity> ) intent.getSerializableExtra( "favoriteitems" );
            Log.d(TAG, "message_sended: "+data);
             fav.setCollection( data );
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver( broadcastforsearchbar);
    }

}
