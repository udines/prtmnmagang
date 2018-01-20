package com.pertamina.pertaminatuban.distribusi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by fata on 1/20/2018.
 */

public class Konsumen {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    @SerializedName("nilai")
    @Expose
    private float nilai;

    @SerializedName("total")
    @Expose
    private float total;

    /*constructor*/
    public Konsumen() {
    }

    public Konsumen(String date, String fuel, float nilai, float total) {
        this.date = date;
        this.fuel = fuel;
        this.nilai = nilai;
        this.total = total;
    }

    /*setter*/

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*getter*/
    public String getDate() {
        return date;
    }

    public String getFuel() {
        return fuel;
    }

    public float getNilai() {
        return nilai;
    }

    public float getTotal() {
        return total;
    }

    public String getId() {
        return id;
    }
}
