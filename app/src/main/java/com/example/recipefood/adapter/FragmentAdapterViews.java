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

    long id;

    public FragmentAdapterViews(@NonNull FragmentManager fm, int behavior, long id) {
        super(fm, behavior);
        this.id = id;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(id);
            case 1:
                return new SearchFragment(id);
            case 2:
                return new RecipeFragment();
            case 3:
                return new UserFragment(id);
        }
        return new HomeFragment(id);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
