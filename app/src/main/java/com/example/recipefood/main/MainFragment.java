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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFragment extends Fragment {
    private ViewPager viewPager;
    private FragmentAdapterViews fragmentAdapterViews;
    private BottomNavigationView bottomBar;
    private long id;

    public MainFragment(long id) {
        this.id = id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        bottomBar = view.findViewById(R.id.bottombar);
        viewPager = view.findViewById(R.id.recyclerView_Random);
        //getData();
        fragmentAdapterViews = new FragmentAdapterViews(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, id);
        viewPager.setAdapter(fragmentAdapterViews);
        Log.d("Id: ", id + "");
        Init();
        return view;
    }


    public void Init() {
        setupViewPager();
    }

    public void setupViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomBar.getMenu().findItem(R.id.home_recipe).setChecked(true);
                        break;
                    case 1:
                        bottomBar.getMenu().findItem(R.id.search_recipe).setChecked(true);
                        break;
                    case 2:
                        bottomBar.getMenu().findItem(R.id.recipe).setChecked(true);
                        break;
                    case 3:
                        bottomBar.getMenu().findItem(R.id.user).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_recipe:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.search_recipe:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.recipe:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.user:
                        viewPager.setCurrentItem(3);
                }
                return false;
            }
        });
    }
//    public void getData(){
//        Bundle bundle = getArguments();
//        username = (String)bundle.get("Username");
//    }
}
