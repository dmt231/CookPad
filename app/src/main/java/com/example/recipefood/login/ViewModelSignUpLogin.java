package com.example.recipefood.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.FoodRepository;
import com.example.recipefood.model.User;
import com.example.recipefood.model.UserRepository;

import java.util.ArrayList;

public class ViewModelSignUpLogin extends ViewModel {
    public MutableLiveData<ArrayList<User>> listUser;

    private MutableLiveData<ArrayList<User>> userAccount;
    private UserRepository userRepository;

    public MutableLiveData<ArrayList<User>> getListUser() {
        listUser = new MutableLiveData<>();
        userRepository = new UserRepository();
        loadData();
        return listUser;
    }

    public MutableLiveData<ArrayList<User>> getUserAccount(long id) {
        userAccount = new MutableLiveData<>();
        userRepository = new UserRepository();
        loadAccount(id);
        return userAccount;
    }

    public void loadData() {
        userRepository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    listUser.setValue(users);
            }
        });
        userRepository.checkUser();
    }

    public void loadAccount(long id) {
        userRepository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    userAccount.setValue(users);
            }
        });
        userRepository.getUserLogin(id);
    }
}
