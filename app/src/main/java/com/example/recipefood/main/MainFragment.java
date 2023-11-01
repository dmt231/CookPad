package com.example.recipefood.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.recipefood.R;
import com.example.recipefood.adapter.FragmentAdapterViews;
import com.example.recipefood.databinding.MainFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFragment extends Fragment {
    private MainFragmentBinding viewBinding;
    private FragmentAdapterViews fragmentAdapterViews;

    private long id;

    public MainFragment(long id) {
        this.id = id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = MainFragmentBinding.inflate(inflater, container, false);
        View view = viewBinding.getRoot();
        //getData();
        fragmentAdapterViews = new FragmentAdapterViews(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, id);
        viewBinding.viewPager.setAdapter(fragmentAdapterViews);
        Init();
        return view;
    }


    public void Init() {
        setupViewPager();
    }

    public void setupViewPager() {
        viewBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewBinding.bottombar.getMenu().findItem(R.id.home_recipe).setChecked(true);
                        break;
                    case 1:
                        viewBinding.bottombar.getMenu().findItem(R.id.search_recipe).setChecked(true);
                        break;
                    case 2:
                        viewBinding.bottombar.getMenu().findItem(R.id.recipe).setChecked(true);
                        break;
                    case 3:
                        viewBinding.bottombar.getMenu().findItem(R.id.user).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewBinding.bottombar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_recipe:
                        viewBinding.viewPager.setCurrentItem(0);
                        return true;
                    case R.id.search_recipe:
                        viewBinding.viewPager.setCurrentItem(1);
                        return true;
                    case R.id.recipe:
                        viewBinding.viewPager.setCurrentItem(2);
                        return true;
                    case R.id.user:
                        viewBinding.viewPager.setCurrentItem(3);
                }
                return false;
            }
        });
    }
}
