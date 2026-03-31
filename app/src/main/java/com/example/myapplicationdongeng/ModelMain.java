package com.example.myapplicationdongeng;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Comparator;

public class ModelMain implements Serializable {

    String strJudul;
    String strCerita;

    public String getStrJudul() {
        return strJudul;
    }

    public void setStrJudul(String strJudul) {
        this.strJudul = strJudul;
    }

    public String getStrCerita() {
        return strCerita;
    }

    public void setStrCerita(String strCerita) {
        this.strCerita = strCerita;
    }

    @SuppressLint("NewApi")
    public static Comparator<ModelMain> sortByAsc = Comparator.comparing(o -> o.strJudul);
}
