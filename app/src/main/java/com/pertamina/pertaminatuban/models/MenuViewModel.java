package com.pertamina.pertaminatuban.models;

/**
 * Created by laptop on 1/16/2018.
 */

public class MenuViewModel {
    private String title;

    /*id drawable digunakan untuk menampilkan gambar pada menu.
    * menggunakan tipe int agar mudah dalam mencari aset gambar
    * yang ada di folder drawable*/
    private int idDrawable;

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
}
