package com.example.recipefood.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.recipefood.login.LoginPageFragment;
import com.example.recipefood.login.SignUpFragment;

public class LoginAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    public LoginAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginPageFragment();
            case 1:
                return new SignUpFragment();
        }

        return new LoginPageFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
