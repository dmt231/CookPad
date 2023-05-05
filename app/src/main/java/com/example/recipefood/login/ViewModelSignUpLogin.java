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
    private Repository mRepository;

    public MutableLiveData<ArrayList<User>> getListUser() {
        listUser = new MutableLiveData<>();
        mRepository = new Repository();
        loadData();
        return listUser;
    }

    public MutableLiveData<ArrayList<User>> getUserAccount(long id) {
        userAccount = new MutableLiveData<>();
        mRepository = new Repository();
        loadAccount(id);
        return userAccount;
    }

    public void loadData() {
        mRepository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    listUser.setValue(users);
            }
        });
        mRepository.checkUser();
    }

    public void loadAccount(long id) {
        mRepository.getUserLiveData().observeForever(new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                if (users != null && !users.isEmpty())
                    userAccount.setValue(users);
            }
        });
        mRepository.getUserLogin(id);
    }
}
