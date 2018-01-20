package com.pertamina.pertaminatuban.distribusi.models;


import java.util.Calendar;

/**
 * Created by laptop on 1/19/2018.
 */

public class Matbal {

    public static final String PERTAMAX = "Pertamax";
    public static final String PERTALITE = "Pertalite";
    public static final String BIOSOLAR = "Biosolar";
    public static final String SOLAR = "";
    public static final String BIOFLAME = "";

    private String date;
    private String fuel;
    private int nilai;
    private int total;

    /*constructor*/
    public Matbal() {
    }

    public Matbal(String date, String fuel, int nilai, int total) {
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

    public void setDate(int y, int m, int d) {
        String year, month, day;
        year = String.valueOf(y);
        month = String.valueOf(m);
        day = String.valueOf(d);
        this.date = year + "-" + month + "-" + day;
    }

    /*getter*/
    public String getDate() {
        return date;
    }

    public String getFuel() {
        return fuel;
    }

    public int getNilai() {
        return nilai;
    }

    public int getTotal() {
        return total;
    }
}
