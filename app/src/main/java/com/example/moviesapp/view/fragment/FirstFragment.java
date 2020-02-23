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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.GridAdapter;
import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.database.MovieDao;
import com.example.moviesapp.database.MovieDatabase;
import com.example.moviesapp.model.MovieEntity;
import com.example.moviesapp.model.MoviesResponse;
import com.example.moviesapp.view.activity.DetailActivity;
import com.example.moviesapp.viewmodel.FirstFragmentViewModel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FirstFragment extends Fragment {
    @BindView(R.id.gird_view)
    GridView gridView;   // https://abhiandroid.com/ui/gridview
    GridAdapter adapter;
    private Context context;
    private static final String TAG = "FirstFragment";
    public static final String BROADCAST_FOR_MOVIES_SEARCHBAR = "broadcast_for_movie_searchbar";
    public static final String BROADCAST_FOR_SEARCH_CLOSE = "broadcast_for_search_close";
    private Boolean isSearching = true;
    private int page = 1;
    private boolean isLoading = false;


    private FirstFragmentViewModel firstFragmentViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter1 = new IntentFilter(BROADCAST_FOR_MOVIES_SEARCHBAR);
        getActivity().registerReceiver(broadcastforsearchbar, intentFilter1);

        IntentFilter intentFilter2 = new IntentFilter(BROADCAST_FOR_SEARCH_CLOSE);
        getActivity().registerReceiver(searchCloseBoreadcastreceiver, intentFilter2);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        adapter = new GridAdapter(getActivity(), new ArrayList<>());
        gridView.setAdapter(adapter);
        setScrollListener();
        setOnItemClickListener();
        setupViewModel();
        loadData();
        return view;
    }

    private void setOnItemClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                MovieEntity clickedDataItem = adapter.getCollection().get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("movies", (Serializable) clickedDataItem);
                startActivity(intent);
            }
        });
    }

    private void setScrollListener() {
        //https://stackoverflow.com/questions/13265457/lazy-download-images-into-gridview/13265776#13265776
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemsCount) {
                if (totalItemsCount > 0) {
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading && isSearching && (lastVisibleItem == totalItemsCount)) {
                        isLoading = true;
                        loadMoreData();

                    }
                }
            }

        });
    }


    private void setupViewModel() {
        firstFragmentViewModel = ViewModelProviders.of(this).get(FirstFragmentViewModel.class);
        firstFragmentViewModel.getMoviesLiveData().observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse != null) {

                    page = moviesResponse.getPage() + 1; //incrementing page number
                    if (adapter != null) {
                        //1
                        adapter.setCollection(moviesResponse.getResults());
                    }
                    isLoading = false;
                }
            }
        });

        firstFragmentViewModel.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        firstFragmentViewModel.getDbMoviesLiveData().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                if (adapter != null) {
                    //2
                    adapter.clearCollection();
                    adapter.setCollection(movieEntities);
                }
            }
        });
    }

    private void loadData() {
        firstFragmentViewModel.loadData(page);
    }

    private void loadMoreData() {

        firstFragmentViewModel.loadData(page);
    }

    //receiving from homeactivity
    private BroadcastReceiver broadcastforsearchbar = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String query = intent.getStringExtra(Appconstant.SEARCH_QUERY);
            Log.d(TAG, "onReceive: " + query);
            isSearching = false;

            if (query != null && !query.isEmpty()) {
                MovieDatabase database = MovieDatabase.getDatabaseInstance(getActivity());
                MovieDao data = database.mDao();
                List<MovieEntity> movieEntityList = data.search("%" + query + "%");
                if (movieEntityList != null && movieEntityList.size() > 0) {
                    Log.d(TAG, "onReceive: " + movieEntityList);
                    adapter.clearCollection();
                    adapter.setCollection(movieEntityList);
                }
            }
        }
    };

    private BroadcastReceiver searchCloseBoreadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive 2: "+"jhdbj");
             isSearching=true;
            firstFragmentViewModel.loadMoviesFromDB();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            getActivity().unregisterReceiver(broadcastforsearchbar);
            getActivity().unregisterReceiver(searchCloseBoreadcastreceiver);
        }
    }

}
