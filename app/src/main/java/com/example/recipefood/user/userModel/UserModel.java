package com.example.recipefood.user.userModel;

import android.content.Context;

import com.example.recipefood.model.User;
import com.example.recipefood.user.userDatabase.UserDatabase;
import com.example.recipefood.user.userDatabase.UserLogin;

public class UserModel {
    public boolean checkUser(int id, Context context){
        UserLogin another = UserDatabase.getInstance(context).daoUser().GetUserFromId(id);
        if(another != null){
            return true;
        }
        return  false;
    }

    public void logoutUser(int id, Context context){
        UserLogin userLogin = new UserLogin(id, 0);
        UserDatabase.getInstance(context).daoUser().UpdateUser(userLogin);
    }

    public UserLogin CurrentUser(Context context){
        UserLogin u = UserDatabase.getInstance(context).daoUser().GetUserLogin();
        return  u;
    }
}
