package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("ObsoleteSdkInt")
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
        Button btnRiwayat = findViewById(R.id.btnRiwayat);
        Button btnTentang = findViewById(R.id.btnTentang);

        // Ke halaman utama (Daftar Cerita)
        btnMulai.setOnClickListener(v -> startActivity(new Intent(SplashActivity.this, MainActivity.class)));

        // Ke halaman Riwayat (Cerita yang sudah dibaca)
        btnRiwayat.setOnClickListener(v -> startActivity(new Intent(SplashActivity.this, HistoryActivity.class)));

        // Klik Tombol Tentang
        btnTentang.setOnClickListener(v -> Toast.makeText(SplashActivity.this, "Aplikasi Kumpulan Cerita Dongeng v1.0", Toast.LENGTH_SHORT).show());
    }
}