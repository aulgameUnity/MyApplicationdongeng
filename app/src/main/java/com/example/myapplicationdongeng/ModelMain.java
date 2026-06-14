package com.example.myapplicationdongeng;

import androidx.annotation.Keep;
import java.io.Serializable;
import java.util.Comparator;

@Keep
public class ModelMain implements Serializable {

    public String strJudul;
    public String strCerita;

    public String getStrJudul() {
        return strJudul; }
    public void setStrJudul(String strJudul) {
        this.strJudul = strJudul; }

    public String getStrCerita() {
        return strCerita; }
    public void setStrCerita(String strCerita) {
        this.strCerita = strCerita; }
    public static Comparator<ModelMain> sortByAsc = (o1, o2) ->
            o1.getStrJudul().compareToIgnoreCase(o2.getStrJudul());
}
