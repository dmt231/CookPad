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
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlogin, container, false);
        mTabLayout = view.findViewById(R.id.tabLayoutLogin);
        mViewPager2 = (ViewPager2) view.findViewById(R.id.viewPagerLogin);

        mTabLayout.addTab(mTabLayout.newTab().setText("LOGIN"));
        mTabLayout.addTab(mTabLayout.newTab().setText("SIGN UP"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginAdapter adapter = new LoginAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        mViewPager2.setAdapter(adapter);


        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mTabLayout.setTranslationY(300);
        mTabLayout.setAlpha(v);
        mTabLayout.animate().translationY(v).alpha(1).setDuration(1000).setStartDelay(100).start();

        return view;
    }
}
