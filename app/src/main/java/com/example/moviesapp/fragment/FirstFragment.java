package com.example.moviesapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.example.moviesapp.activity.DetailActivity;
import com.example.moviesapp.adapter.GridAdapter;
import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.retrofit.BaseApplication;
import com.example.moviesapp.retrofit.FCMAPI;
import com.example.moviesapp.model.Movie;
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
    List<Movie> movies;
    private static final String TAG = "FirstFragment";
    public static final String THIS_BROADCAST_FOR_CONTACT_SEARCHBAR = "this is for contact searchBar";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        Retrofit();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter1=new IntentFilter( THIS_BROADCAST_FOR_CONTACT_SEARCHBAR );
        getActivity().registerReceiver( broadcastforsearchbar,intentFilter1 );
        super.onCreate(savedInstanceState);
    }



    private void Retrofit() {
        // https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
        //Instanceretrofit
        Retrofit retrofit = BaseApplication.getRetrofitInstance();
        //interface
        FCMAPI api = retrofit.create(FCMAPI.class);
        api.getPopularMovies(Appconstant.THE_MOVIE_DB_API_TOKEN).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.message());


                    movies = response.body().getResults();
                    Log.d(TAG, "onResponse: "+movies.size());
                    adapter = new GridAdapter(getActivity(), movies);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // set an Intent to Another Activity
                            Movie clickedDataItem = movies.get(position);
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("movies", (Serializable) clickedDataItem);
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
        //https://stackoverflow.com/questions/13265457/lazy-download-images-into-gridview/13265776#13265776
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if (isScrolling && (visibleItemCount + firstVisibleItem == totalItemCount)) {
                    isScrolling = false;

                   // fetchdata();
                }

            }

        });
    }
    private BroadcastReceiver broadcastforsearchbar=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Movie> data = (List<Movie>) intent.getSerializableExtra( "contactdata" );
            Log.d(TAG, "message_sendedhv: "+data.size());
            if(data.size()==0){
                adapter.setCollection(movies);
            }
            else{
                adapter.setCollection(data);
            }
        }
    };
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver( broadcastforsearchbar);
        super.onDestroy();
    }
  /*  private void fetchdata() {
        progress.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
            }
        }, 1000);
    }*/


}
