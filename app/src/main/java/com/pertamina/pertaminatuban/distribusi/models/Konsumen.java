package com.pertamina.pertaminatuban.distribusi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Konsumen {

    public static final String SPBU = "SPBU";
    public static final String TNI = "TNI/Polri";
    public static final String SPDN = "SPDN";
    public static final String PLN = "PLN";
    public static final String MT = "Own Use MT";

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

    @SerializedName("konsumen")
    @Expose
    private String konsumen;

    /*constructor*/

    public Konsumen(String id, String date, String konsumen, String fuel, float nilai) {
        this.id = id;
        this.date = date;
        this.fuel = fuel;
        this.nilai = nilai;
        this.konsumen = konsumen;
    }

    public Konsumen(String date, String konsumen, String fuel, float nilai) {
        this.date = date;
        this.konsumen = konsumen;
        this.fuel = fuel;
        this.nilai = nilai;
    }

    public Konsumen() {
    }
    /*setter*/

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setNilai(float nilai) {
        this.nilai = nilai;
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
