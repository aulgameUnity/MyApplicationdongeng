package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private final List<ModelMain> listHistory = new ArrayList<>();
    private MainAdapter mainAdapter;
    private TextView tvNoHistory;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }

        if (Build.VERSION.SDK_INT >= 21) {
            MainActivity.setWindowFlag(
                    this,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    false
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        RecyclerView rvHistoryDongeng = findViewById(R.id.rvHistoryDongeng);
        tvNoHistory = findViewById(R.id.tvNoHistory);

        rvHistoryDongeng.setLayoutManager(new LinearLayoutManager(this));

        mainAdapter = new MainAdapter(this, new ArrayList<>());
        rvHistoryDongeng.setAdapter(mainAdapter);

        loadHistory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHistory();
    }

    private void loadHistory() {

        SharedPreferences sp =
                getSharedPreferences("PREF_HISTORY", MODE_PRIVATE);

        String json = sp.getString("list_history", null);

        if (json == null || json.isEmpty()) {
            tvNoHistory.setVisibility(View.VISIBLE);
            mainAdapter.setModelMainList(new ArrayList<>());
            return;
        }

        try {
            Gson gson = new Gson();

            Type type =
                    new TypeToken<ArrayList<ModelMain>>() {
                    }.getType();

            List<ModelMain> history =
                    gson.fromJson(json, type);

            if (history != null && !history.isEmpty()) {

                tvNoHistory.setVisibility(View.GONE);

                listHistory.clear();
                listHistory.addAll(history);

                mainAdapter.setModelMainList(
                        new ArrayList<>(listHistory)
                );

            } else {

                tvNoHistory.setVisibility(View.VISIBLE);
                mainAdapter.setModelMainList(
                        new ArrayList<>()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            tvNoHistory.setVisibility(View.VISIBLE);

            mainAdapter.setModelMainList(
                    new ArrayList<>()
            );
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}