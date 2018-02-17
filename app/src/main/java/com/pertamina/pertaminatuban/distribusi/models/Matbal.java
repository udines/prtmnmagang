package com.pertamina.pertaminatuban.distribusi.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

public class Matbal {

    public static final String PERTAMAX = "Pertamax, BULK";
    public static final String PERTALITE = "Pertalite";
    public static final String PREMIUM = "Premium";
    public static final String BIOSOLAR = "Biosolar";
    public static final String SOLAR = "SOLAR/HSD/MGO/ADO";
    public static final String BIOFLAME = "Biofame";

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

    /*constructor*/
    public Matbal() {
    }

    public Matbal(String date, String fuel, float nilai) {
        this.date = date;
        this.fuel = fuel;
        this.nilai = nilai;
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
}
