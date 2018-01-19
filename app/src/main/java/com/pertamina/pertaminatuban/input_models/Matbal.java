package com.pertamina.pertaminatuban.input_models;


import java.util.Calendar;

/**
 * Created by laptop on 1/19/2018.
 */

public class Matbal {

    private final String PERTAMAX = "";
    private final String PERTALITE = "";
    private final String BIOSOLAR = "";
    private final String SOLAR = "";
    private final String BIOFLAME = "";

    private String date;
    private int fuel;
    private int nilai;
    private int total;

    /*constructor*/
    public Matbal() {
    }

    public Matbal(String date, int fuel, int nilai, int total) {
        this.date = date;
        this.fuel = fuel;
        this.nilai = nilai;
        this.total = total;
    }

    /*setter*/
    public void setFuel(int fuel) {
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

    public int getFuel() {
        return fuel;
    }

    public int getNilai() {
        return nilai;
    }

    public int getTotal() {
        return total;
    }
}
