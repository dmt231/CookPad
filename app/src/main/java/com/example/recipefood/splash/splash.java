package com.example.recipefood.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.recipefood.MainActivity;
import com.example.recipefood.R;
import com.example.recipefood.user.userDatabase.UserDatabase;
import com.example.recipefood.user.userDatabase.UserLogin;

public class splash extends AppCompatActivity {
    private final int DELAY_TIME = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            UserLogin userLogin = UserDatabase.getInstance(this).daoUser().GetUserLogin();
            if (userLogin == null){
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(splash.this, MainActivity.class);
                intent.putExtra("UserId", userLogin.getUserId());
                startActivity(intent);
            }
            finish();
        }, DELAY_TIME);
    }
}