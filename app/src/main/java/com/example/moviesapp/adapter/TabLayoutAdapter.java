package com.example.moviesapp.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.moviesapp.view.fragment.FirstFragment;
import com.example.moviesapp.view.fragment.SecondFragment;

import java.util.List;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "TabLayoutAdapter";
    private List<String> List1;

    public TabLayoutAdapter(FragmentManager fm, List<String> List) {
        super( fm );
        Log.d( TAG, "TabLayoutAdapter: "+fm );
        Log.d( TAG, "TabLayoutAdapter: "+List );
        List1=List;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();

                default:
                    return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return List1.get( position );                       //title
    }

    @Override
    public int getCount() {
        return List1.size();
    }
}
