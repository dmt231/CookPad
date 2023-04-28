package com.example.recipefood.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.Repository;
import com.example.recipefood.model.User;

import java.util.ArrayList;

public class ViewModelSignUpLogin extends ViewModel {
    public MutableLiveData<ArrayList<User>> listUser;
    private Repository repository;

    public MutableLiveData<ArrayList<User>> getListUser() {
        listUser = new MutableLiveData<>();
        repository = new Repository();
        loadData();
        return listUser;
    }

    public void loadData() {
        repository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    listUser.setValue(users);
            }
        });
        repository.checkUser();
    }
}
