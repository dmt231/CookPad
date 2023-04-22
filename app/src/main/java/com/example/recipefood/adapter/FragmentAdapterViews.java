package com.example.recipefood.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.recipefood.home.HomeFragment;
import com.example.recipefood.search.SearchFragment;
import com.example.recipefood.user.UserFragment;
import com.example.recipefood.views.RecipeFragment;

public class FragmentAdapterViews extends FragmentStatePagerAdapter {


    public FragmentAdapterViews(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new RecipeFragment();
            case 3 :
                return new UserFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
