package com.example.recipefood.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recipefood.R;
import com.example.recipefood.adapter.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlogin, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutLogin);
        viewPager2 = (ViewPager2) view.findViewById(R.id.viewPagerLogin);

        tabLayout.addTab(tabLayout.newTab().setText("LOGIN"));
        tabLayout.addTab(tabLayout.newTab().setText("SIGN UP"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginAdapter adapter = new LoginAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(v).alpha(1).setDuration(1000).setStartDelay(100).start();

        return view;
    }
}
