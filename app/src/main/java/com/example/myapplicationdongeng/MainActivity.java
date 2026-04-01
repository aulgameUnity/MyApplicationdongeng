package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    List<ModelMain> modelMain = new ArrayList<>();
    MainAdapter mainAdapter;
    RecyclerView rvListDongeng;
    SearchView searchTanaman;
    ProgressBar progressBar;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        rvListDongeng = findViewById(R.id.rvListDongeng);
        searchTanaman = findViewById(R.id.searchTanaman);
        progressBar = findViewById(R.id.progressBar);

        //transparent background search view
        View searchPlate = searchTanaman.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        searchTanaman.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchTanaman.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mainAdapter != null) {
                    mainAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        rvListDongeng.setLayoutManager(new LinearLayoutManager(this));
        rvListDongeng.setHasFixedSize(true);

        mainAdapter = new MainAdapter(this, modelMain);
        rvListDongeng.setAdapter(mainAdapter);

        //get data JSON in background thread
        getDataDongeng();
    }

    private void getDataDongeng() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                InputStream stream = getAssets().open("ceritadongeng.json");
                int size = stream.available();
                byte[] buffer = new byte[size];
                stream.read(buffer);
                stream.close();
                String strContent = new String(buffer, StandardCharsets.UTF_8);
                
                List<ModelMain> tempList = getModelMains(strContent);
                Collections.sort(tempList, ModelMain.sortByAsc);

                runOnUiThread(() -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    mainAdapter.setModelMainList(tempList);
                });
            } catch (IOException | JSONException e) {
                runOnUiThread(() -> {
                    if (progressBar != null) progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Ups, ada yang tidak beres.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @NonNull
    private static List<ModelMain> getModelMains(String strContent) throws JSONException {
        JSONObject jsonObject = new JSONObject(strContent);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        List<ModelMain> tempList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            ModelMain dataApi = new ModelMain();
            dataApi.setStrCerita(object.getString("file"));
            dataApi.setStrJudul(object.getString("title"));
            tempList.add(dataApi);
        }
        return tempList;
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
