package com.example.moviesapp.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Movie;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieEntity;
import com.example.moviesapp.model.database.DaoDatabase;
import com.example.moviesapp.model.database.MainDatabase;
import com.example.moviesapp.view.activity.DetailActivity;
import com.example.moviesapp.adapter.GridAdapter;
import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.model.retrofit.BaseApplication;
import com.example.moviesapp.model.retrofit.MovieAPI;
import com.example.moviesapp.model.MoviesResponse;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FirstFragment extends Fragment {
    @BindView(R.id.gird_view)
    GridView gridView;   // https://abhiandroid.com/ui/gridview
    Boolean isScrolling = false;
    GridAdapter adapter;
    private int totalItems;
    private boolean isLoading = false;
    private int page = 1;

    private static final String TAG = "FirstFragment";
    public static final String THIS_BROADCAST_FOR_MOVIES_SEARCHBAR = "this is for movie searchBar";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter1=new IntentFilter( THIS_BROADCAST_FOR_MOVIES_SEARCHBAR );
        getActivity().registerReceiver( broadcastforsearchbar,intentFilter1 );
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        fetchData();
        setOnItemClickListener();
        return view;
    }

    private void fetchData() {
        // https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
        //Instanceretrofit
        Retrofit retrofit = BaseApplication.getRetrofitInstance();
        //interface
        MovieAPI api = retrofit.create(MovieAPI.class);
        api.getPopularMovies(Appconstant.THE_MOVIE_DB_API_TOKEN,page).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.message());

                    MoviesResponse moviesResponse = response.body();

                    totalItems = moviesResponse.getTotalResults();
                    page++;
                    List<MovieEntity> movieEntities = moviesResponse.getResults();

                    adapter = new GridAdapter(getActivity(), movieEntities);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // set an Intent to Another Activity
                            MovieEntity clickedDataItem = movieEntities.get(position);
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("movieEntities", (Serializable) clickedDataItem);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: unsucessfull");

            }
        });
    }
    private void setOnItemClickListener(){
        //https://stackoverflow.com/questions/13265457/lazy-download-images-into-gridview/13265776#13265776
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (totalItemCount > 0) {
                    int lastVisibleItem = firstVisibleItem + visibleItemCount;
                    if (!isLoading  && (lastVisibleItem == totalItemCount)) {
                        isLoading = true;
                        loadMoreData();

                    }
                }

            }

        });
    }
    private void loadMoreData() {
        Retrofit retrofit = BaseApplication.getRetrofitInstance();
        MovieAPI api = retrofit.create(MovieAPI.class);
        api.getPopularMovies(Appconstant.THE_MOVIE_DB_API_TOKEN, page).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.message());

                    MoviesResponse moviesResponse = response.body();
                    page++;

                    List<MovieEntity> movies = moviesResponse.getResults();

                    if (adapter != null) {
                        adapter.setCollection(movies);
                    }

                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: unsucessfull");

            }
        });

    }

    private BroadcastReceiver broadcastforsearchbar=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String query = intent.getStringExtra( "searchdata" );
            if (query != null && !query.isEmpty()) {
                MainDatabase database = MainDatabase.getDatabaseInstance(getActivity());
                DaoDatabase data = database.mDao();
                List<MovieEntity> movieEntityList = data.search("%" + query + "%");

                if (movieEntityList != null && movieEntityList.size() > 0) {
                    adapter.clearCollection();
                    adapter.setCollection(movieEntityList);
                }
            }
        }
    };
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver( broadcastforsearchbar);
        super.onDestroy();
    }


}
