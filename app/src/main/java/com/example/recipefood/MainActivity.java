package com.example.recipefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.recipefood.adapter.FragmentAdapterViews;
import com.example.recipefood.model.RecipeInstrument;
import com.example.recipefood.model.Repository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewpager;
    FragmentAdapterViews fragmentAdapterViews;

    BottomNavigationView bottombar;

    Repository database;
    ArrayList<RecipeInstrument> listRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

    }

    public void Init(){
        bottombar = findViewById(R.id.bottombar);
        viewpager = findViewById(R.id.recyclerView_Random);
        fragmentAdapterViews = new FragmentAdapterViews(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(fragmentAdapterViews);
        setupViewPager();

    }

    public void setupViewPager(){
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottombar.getMenu().findItem(R.id.home_recipe).setChecked(true);
                        break;
                    case 1:
                        bottombar.getMenu().findItem(R.id.search_recipe).setChecked(true);
                        break;
                    case 2:
                        bottombar.getMenu().findItem(R.id.recipe).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottombar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_recipe:
                        viewpager.setCurrentItem(0);
                        return true;
                    case R.id.search_recipe:
                        viewpager.setCurrentItem(1);
                        return true;
                    case R.id.recipe:
                        viewpager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });
    }

}