package com.example.recipefood.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.recipefood.model.Repository;
import com.example.recipefood.model.User;

import java.util.ArrayList;

public class ViewModelSignUpLogin extends ViewModel {
    public MutableLiveData<ArrayList<User>> listUser;

    private MutableLiveData<ArrayList<User>> userAccount;
    private Repository repository;

    public MutableLiveData<ArrayList<User>> getListUser() {
        listUser = new MutableLiveData<>();
        repository = new Repository();
        loadData();
        return listUser;
    }

    public MutableLiveData<ArrayList<User>> getUserAccount(long id) {
        userAccount = new MutableLiveData<>();
        repository = new Repository();
        loadAccount(id);
        return userAccount;
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

    public void loadAccount(long id) {
        repository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    userAccount.setValue(users);
            }
        });
        repository.getUserLogin(id);
    }
}
