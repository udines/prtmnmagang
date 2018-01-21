package com.pertamina.pertaminatuban.distribusi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("konsumen")
    @Expose
    private String konsumen;

    /*constructor*/
    public Konsumen() {
    }

    public Konsumen(String date, String konsumen, String fuel, float nilai) {
        this.date = date;
        this.konsumen = konsumen;
        this.fuel = fuel;
        this.nilai = nilai;
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

    public void setKonsumen(String konsumen) {
        this.konsumen = konsumen;
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

    public String getKonsumen() {
        return konsumen;
    }

    public String getFuelKonsumen() {
        return fuel + ";" + konsumen;
    }
}
