package com.example.recipefood.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.recipefood.MainActivity;
import com.example.recipefood.R;
import com.example.recipefood.model.Repository;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash extends AppCompatActivity {
    private final int DELAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseMessaging.getInstance().subscribeToTopic("new_recipe");

        new Handler().postDelayed(() -> {
            int id = new Repository().checkLogged(this);
            Intent intent = new Intent(Splash.this, MainActivity.class);
            if (id != -1) {
                intent.putExtra("UserId", id);
            }
            startActivity(intent);
            finish();
        }, DELAY_TIME);
    }
}