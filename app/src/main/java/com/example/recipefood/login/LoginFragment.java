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
    private FragmentloginBinding binding;
    float v = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentloginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.tabLayoutLogin.addTab(binding.tabLayoutLogin.newTab().setText("LOGIN"));
        binding.tabLayoutLogin.addTab(binding.tabLayoutLogin.newTab().setText("SIGN UP"));
        binding.tabLayoutLogin.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginAdapter adapter = new LoginAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        binding.viewPagerLogin.setAdapter(adapter);


        binding.viewPagerLogin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayoutLogin.selectTab(binding.tabLayoutLogin.getTabAt(position));
            }
        });
        binding.tabLayoutLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPagerLogin.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.tabLayoutLogin.setTranslationY(300);
        binding.tabLayoutLogin.setAlpha(v);
        binding.tabLayoutLogin.animate().translationY(v).alpha(1).setDuration(1000).setStartDelay(100).start();

        return view;
    }
}
