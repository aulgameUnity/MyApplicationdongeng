package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_DONGENG = "DETAIL_DONGENG";

    String strJudul, strCerita;
    ModelMain modelMain;

    Toolbar toolbar;
    TextView tvJudul, tvCerita;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        toolbar = findViewById(R.id.toolbar);
        tvJudul = findViewById(R.id.tvJudul);
        tvCerita = findViewById(R.id.tvCerita);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        modelMain = (ModelMain) getIntent().getSerializableExtra(DETAIL_DONGENG);

        if (modelMain != null) {
            strJudul = modelMain.getStrJudul();
            strCerita = modelMain.getStrCerita();

            tvJudul.setText(strJudul);

            // Cek jika strCerita null agar tidak error saat diproses Html.fromHtml
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvCerita.setText(Html.fromHtml(strCerita, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    tvCerita.setText(Html.fromHtml(strCerita));
            }

            // Simpan ke riwayat baca
            saveToHistory(modelMain);
        }
    }

    private void saveToHistory(ModelMain model) {

        SharedPreferences sp =
                getSharedPreferences("PREF_HISTORY", MODE_PRIVATE);

        Gson gson = new Gson();

        String json =
                sp.getString("list_history", "[]");

        Type type =
                new TypeToken<ArrayList<ModelMain>>() {
                }.getType();

        ArrayList<ModelMain> historyList =
                gson.fromJson(json, type);

        if (historyList == null) {
            historyList = new ArrayList<>();
        }

        for (int i = historyList.size() - 1; i >= 0; i--) {
            if (historyList.get(i).getStrJudul()
                    .equals(model.getStrJudul())) {
                historyList.remove(i);
            }
        }

        historyList.add(0, model);

        if (historyList.size() > 20) {
            historyList.remove(historyList.size() - 1);
        }

        sp.edit()
                .putString(
                        "list_history",
                        gson.toJson(historyList)
                )
                .apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
}
