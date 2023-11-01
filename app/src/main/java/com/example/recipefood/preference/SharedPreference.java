package com.example.recipefood.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    public SharedPreference(){

    }

    public int checkLogged(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userId", -1);
    }

    public void deleteUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userId");
        editor.apply();
    }

    public void keepLoggedInUser(int id, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", id);
        editor.apply();
    }
}
