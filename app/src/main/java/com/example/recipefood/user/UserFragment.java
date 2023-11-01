package com.example.recipefood.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.recipefood.R;
import com.example.recipefood.databinding.FragmentUserBinding;
import com.example.recipefood.login.ViewModelSignUpLogin;
import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.model.User;
import com.example.recipefood.preference.SharedPreference;
import com.example.recipefood.splash.Splash;
import com.example.recipefood.user.create.CreateRecipe;
import com.example.recipefood.user.userfavorite.FavoriteRecipe;
import com.example.recipefood.user.userrecipe.myFood;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    private FragmentUserBinding viewBinding;
    private ViewModelSignUpLogin viewModel;
    private long id;
    private AlertDialog.Builder alertDialog;
    public UserFragment(long id){
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewBinding = FragmentUserBinding.inflate(inflater, container, false);
        View view = viewBinding.getRoot();
        viewBinding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Do you want to sign out ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new SharedPreference().deleteUser(getContext());
                                Toast.makeText(getContext(), "Logout user ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext().getApplicationContext(), Splash.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
        viewModel = new ViewModelProvider(this).get(ViewModelSignUpLogin.class);
        setDataAccount();
        viewBinding.accountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToCreate();
            }
        });
        viewBinding.accountMyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangedToMyFood();
            }
        });
        viewBinding.accountFavorite.setOnClickListener(new View.OnClickListener() {
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
                        viewBinding.accountUsername.setText(user.getUsername());
                        viewBinding.accountEmail.setText(user.getEmail());
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
