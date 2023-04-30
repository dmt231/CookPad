package com.example.recipefood.user;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.recipefood.R;
import com.example.recipefood.login.ViewModelSignUpLogin;
import com.example.recipefood.model.User;
import com.example.recipefood.user.create.CreateRecipe;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    private ImageView favorite;
    private ImageView create;
    private ImageView myFood;
    private TextView username;
    private TextView email;

    private ViewModelSignUpLogin viewModel;
    private long id;
    public UserFragment(long id){
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Log.d("ID User Fragment : " , id + "");
        username = view.findViewById(R.id.account_username);
        email = view.findViewById(R.id.account_email);
        favorite = view.findViewById(R.id.account_favorite);
        create = view.findViewById(R.id.account_create);
        myFood = view.findViewById(R.id.account_my_food);
        viewModel = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        setDataAccount();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToCreate();
            }
        });
        return view;
    }
    public void setDataAccount(){
        viewModel.getUserAccount(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                for (User user : users ) {
                    Log.d("Info User 1 : ", user.getUsername());
                    if(user.getUserId()==id){
                        username.setText(user.getUsername());
                        email.setText(user.getEmail());
                        break;
                    }
                }
            }
        });
    }
    public void onChangedToCreate(){
        Fragment create = new CreateRecipe();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_user, create);
        fragmentTransaction.commit();
    }
}
