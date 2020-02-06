package com.example.moviesapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.GridAdapter;
import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.retrofit.BaseApplication;
import com.example.moviesapp.retrofit.FCMAPI;
import com.example.moviesapp.retrofit.Movie;
import com.example.moviesapp.retrofit.MoviesResponse;

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
    private static final String TAG = "FirstFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind( this, view );
        Retrofit();
        return view;
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

                    List<Movie> movies = response.body().getResults();
                    GridAdapter adapter=new GridAdapter(getActivity(), movies);
                     gridView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: unsucessfull");

            }
        });

    }

}
