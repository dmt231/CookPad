package com.example.recipefood.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.recipefood.R;
import com.example.recipefood.adapter.LoginAdapter;
import com.example.recipefood.databinding.FragmentloginBinding;
import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment {
    private FragmentloginBinding viewBinding;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentloginBinding.inflate(inflater, container, false);
        View view = viewBinding.getRoot();


        viewBinding.tabLayoutLogin.addTab(viewBinding.tabLayoutLogin.newTab().setText("LOGIN"));
        viewBinding.tabLayoutLogin.addTab(viewBinding.tabLayoutLogin.newTab().setText("SIGN UP"));
        viewBinding.tabLayoutLogin.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginAdapter adapter = new LoginAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        viewBinding.viewPagerLogin.setAdapter(adapter);


        viewBinding.viewPagerLogin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewBinding.tabLayoutLogin.selectTab(viewBinding.tabLayoutLogin.getTabAt(position));
            }
        });
        viewBinding.tabLayoutLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewBinding.viewPagerLogin.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewBinding.tabLayoutLogin.setTranslationY(300);
        viewBinding.tabLayoutLogin.setAlpha(v);
        viewBinding.tabLayoutLogin.animate().translationY(v).alpha(1).setDuration(1000).setStartDelay(100).start();

        return view;
    }
}
