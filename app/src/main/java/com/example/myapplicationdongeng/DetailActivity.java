package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    // Key untuk
    // mengambil data Dari Intent
    public static final String DETAIL_DONGENG = "DETAIL_DONGENG";

    // Variable untuk menyimpan data judul dan cerita
    String strJudul, strCerita;

    // Object model (data dongeng)
    ModelMain modelMain;

    // Komponen UI
    Toolbar toolbar;
    TextView tvJudul, tvCerita;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghubungkan activity dengan layout XML
        setContentView(R.layout.activity_detail);

        // Membuat status bar transparan (untuk Android Marshmallow ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        // Mengatur warna status bar menjadi transparan
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // Menghubungkan variabel dengan komponen di XML
        toolbar = findViewById(R.id.toolbar);
        tvJudul = findViewById(R.id.tvJudul);
        tvCerita = findViewById(R.id.tvCerita);

        // Mengatur toolbar sebagai ActionBar
        setSupportActionBar(toolbar);

        // Pastikan ActionBar tidak null
        assert getSupportActionBar() != null;

        // Menampilkan tombol back di toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Menyembunyikan judul default toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Mengambil data Dari Intent (yang dikirim dari activity sebelumnya)
        modelMain = (ModelMain) getIntent().getSerializableExtra(DETAIL_DONGENG);

        // Jika data tidak kosong
        if (modelMain != null) {

            // Ambil judul dan cerita dari model
            strJudul = modelMain.getStrJudul();
            strCerita = modelMain.getStrCerita();

            // Tampilkan judul ke TextView
            tvJudul.setText(strJudul);

            // Menampilkan cerita (menggunakan HTML format)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvCerita.setText(Html.fromHtml(strCerita, Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvCerita.setText(Html.fromHtml(strCerita));
            }
        }
    }

    // Fungsi ketika tombol di toolbar ditekan
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Jika tombol back ditekan
        if (item.getItemId() == android.R.id.home) {
            finish(); // menutup activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Fungsi untuk mengatur flag pada window (digunakan untuk status bar)
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        // Ambil window dari activity
        Window window = activity.getWindow();

        // Ambil atribut window
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        // Jika ingin mengaktifkan flag
        if (on) {
            layoutParams.flags |= bits;
        }
        // Jika ingin mematikan flag
        else {
            layoutParams.flags &= ~bits;
        }

        // Terapkan perubahan
        window.setAttributes(layoutParams);
    }
}