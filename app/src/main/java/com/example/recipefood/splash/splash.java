package com.example.recipefood.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.recipefood.MainActivity;
import com.example.recipefood.R;
import com.example.recipefood.model.Repository;

public class splash extends AppCompatActivity {
    private final int DELAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            int id = new Repository().checkLogged(this);
            if (id == -1) {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(splash.this, MainActivity.class);
                intent.putExtra("UserId", id);
                startActivity(intent);
            }
            finish();
        }, DELAY_TIME);
    }
}