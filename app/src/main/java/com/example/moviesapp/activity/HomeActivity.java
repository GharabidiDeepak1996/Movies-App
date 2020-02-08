package com.example.moviesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.TabLayoutAdapter;
import com.example.moviesapp.database.DaoDatabase;
import com.example.moviesapp.database.MainDatabase;
import com.example.moviesapp.model.Movie;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviesapp.fragment.FirstFragment.THIS_BROADCAST_FOR_CONTACT_SEARCHBAR;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.search_bar)
    MaterialSearchView materialSearchView;
    TabLayoutAdapter tabLayoutAdapter;
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
         tabLayoutAdapter = new TabLayoutAdapter( getSupportFragmentManager(), list );
        viewPager.setAdapter( tabLayoutAdapter );

        tabLayout.setupWithViewPager( viewPager );
        materialSearchView.setOnQueryTextListener( new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainDatabase database = MainDatabase.getDatabaseInstance(HomeActivity.this);
                DaoDatabase data = database.mDao();
                List<Movie> data1= data.search(newText);

                Intent intent=new Intent( THIS_BROADCAST_FOR_CONTACT_SEARCHBAR );
                intent.putExtra( "contactdata", (Serializable) data1);
                sendBroadcast( intent );
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
    public TabLayoutAdapter getAdapter() {
        return tabLayoutAdapter;
    }
}
