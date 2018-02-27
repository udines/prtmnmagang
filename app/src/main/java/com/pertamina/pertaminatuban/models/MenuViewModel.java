package com.pertamina.pertaminatuban.models;

import java.util.ArrayList;

public class MenuViewModel {
    private String title;

    /*id drawable digunakan untuk menampilkan gambar pada menu.
    * menggunakan tipe int agar mudah dalam mencari aset gambar
    * yang ada di folder drawable*/
    private int idDrawable;

    private ArrayList<String> noBill;

    public MenuViewModel(String title, int idDrawable) {
        this.title = title;
        this.idDrawable = idDrawable;
    }

    public String getTitle() {
        return title;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public ArrayList<String> getNoBill() {
        return noBill;
    }

    public void setNoBill(ArrayList<String> noBill) {
        this.noBill = noBill;
    }
}
