package com.example.recipefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.recipefood.adapter.FragmentAdapterViews;
import com.example.recipefood.home.HomeFragment;
import com.example.recipefood.login.LoginFragment;
import com.example.recipefood.login.LoginPageFragment;
import com.example.recipefood.main.MainFragment;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginPageFragment.onChangedScreen {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent = getIntent();
        int id = intent.getIntExtra("UserId", -1);

        if (id == -1) {
            fragmentTransaction.add(R.id.layout_main, new LoginFragment());
        } else {
            fragmentTransaction.add(R.id.layout_main, new MainFragment(id));
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onChanged(Long id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFragment = new MainFragment(id);
        fragmentTransaction.replace(R.id.layout_main, mainFragment);
        fragmentTransaction.commitNow();
    }
}