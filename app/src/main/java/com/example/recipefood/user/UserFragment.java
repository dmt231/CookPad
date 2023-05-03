package com.example.recipefood.user;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.recipefood.MainActivity;
import com.example.recipefood.R;
import com.example.recipefood.login.ViewModelSignUpLogin;
import com.example.recipefood.model.Repository;
import com.example.recipefood.model.User;
import com.example.recipefood.splash.splash;
import com.example.recipefood.user.create.CreateRecipe;
import com.example.recipefood.user.userfavorite.FavoriteRecipe;
import com.example.recipefood.user.userrecipe.myFood;

import java.util.ArrayList;
import java.util.Objects;

public class UserFragment extends Fragment {
    private ImageView favorite;
    private ImageView create;
    private ImageView myFood;
    private TextView username;
    private TextView email;
    private TextView SignOut;
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
        SignOut = view.findViewById(R.id.sign_out);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Repository().deleteUser(getContext());
                Toast.makeText(getContext(), "Logout user ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext().getApplicationContext(), splash.class);
                startActivity(intent);
            }
        });
        viewModel = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        setDataAccount();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToCreate();
            }
        });
        myFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToMyFood();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToLikeFood();
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
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", (int)id);
        create.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, create);
        fragmentTransaction.addToBackStack(create.getTag());
        fragmentTransaction.commit();
    }
    public void onChangedToMyFood(){
        Fragment myFood = new myFood();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", (int)id);
        myFood.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, myFood);
        fragmentTransaction.addToBackStack(myFood.getTag());
        fragmentTransaction.commit();
    }
    // Sign Out accout
    private final View.OnClickListener evenLogout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Repository().deleteUser(getContext());
            Toast.makeText(getContext(), "Logout user ", Toast.LENGTH_SHORT).show();
        }
    };
    public void onChangedToLikeFood(){
        Fragment likeFood = new FavoriteRecipe();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("Userid", (int)id);
        likeFood.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, likeFood);
        fragmentTransaction.addToBackStack(likeFood.getTag());
        fragmentTransaction.commit();
    }
}
