package com.example.moviesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.search_bar)
    MaterialSearchView materialSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind( this );

        toolbar.setTitle( "MoviesApp" );
        setSupportActionBar( toolbar );

        List<String> list = new ArrayList<>();
        list.add( "Top Rated Movies" );
        list.add( "Favourite Items" );
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter( getSupportFragmentManager(), list );
        viewPager.setAdapter( tabLayoutAdapter );

        tabLayout.setupWithViewPager( viewPager );
        materialSearchView.setOnQueryTextListener( new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate( R.menu.menu_item, menu );
        MenuItem menuItem = menu.findItem( R.id.action_search );
        materialSearchView.setMenuItem( menuItem );
        return true;
    }

}
