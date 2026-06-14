package com.example.myapplicationdongeng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Menunggu selama 3 detik sebelum masuk ke Splash Screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
