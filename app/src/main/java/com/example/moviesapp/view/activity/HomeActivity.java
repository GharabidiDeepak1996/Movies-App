package com.example.moviesapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviesapp.R;

import com.example.moviesapp.adapter.TabLayoutAdapter;
import com.example.moviesapp.constant.Appconstant;
import com.example.moviesapp.view.fragment.FirstFragment;
import com.google.android.material.tabs.TabLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviesapp.view.fragment.FirstFragment.BROADCAST_FOR_MOVIES_SEARCHBAR;


public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
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
    MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar.setTitle("MoviesApp");
        setSupportActionBar(toolbar);

        List<String> list = new ArrayList<>();
        list.add("Top Rated Movies");
        list.add("Favourite Items");
        tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(tabLayoutAdapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               //send to first fragment
                Intent intent = new Intent(BROADCAST_FOR_MOVIES_SEARCHBAR);
                intent.putExtra(Appconstant.SEARCH_QUERY, newText);
                sendBroadcast(intent);
                return false;
            }
        });

        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                Intent intent = new Intent(FirstFragment.BROADCAST_FOR_SEARCH_CLOSE);
                sendBroadcast(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(searchMenuItem);
        return true;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        }
        if (position == 1) {
            searchMenuItem.setVisible(false);
        } else {
            searchMenuItem.setVisible(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
