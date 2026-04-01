package com.example.myapplicationdongeng;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Set transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            MainActivity.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Button btnMulai = findViewById(R.id.btnMulai);
        Button btnDaftar = findViewById(R.id.btnDaftar);
        Button btnTentang = findViewById(R.id.btnTentang);

        // Klik Tombol Mulai atau Daftar Dongeng untuk ke halaman utama
        btnMulai.setOnClickListener(v -> startActivity(new Intent(SplashActivity.this, MainActivity.class)));

        btnDaftar.setOnClickListener(v -> startActivity(new Intent(SplashActivity.this, MainActivity.class)));

        // Klik Tombol Tentang (Bisa Anda isi dengan info pembuat)
        btnTentang.setOnClickListener(v -> {
            // Contoh: Munculkan pesan singkat
            android.widget.Toast.makeText(SplashActivity.this, "Aplikasi Kumpulan Cerita Dongeng v1.0", android.widget.Toast.LENGTH_SHORT).show();
        });
    }
}