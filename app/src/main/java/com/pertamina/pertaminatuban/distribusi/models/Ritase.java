package com.pertamina.pertaminatuban.distribusi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ritase {

    @SerializedName("jumlahMobil")
    @Expose
    private int jumlahMobil;

    @SerializedName("dayaAngkut")
    @Expose
    private int dayaAngkut;

    @SerializedName("tpHarian")
    @Expose
    private double tpHarian;

    @SerializedName("ritase")
    @Expose
    private double ritase;

    @SerializedName("date")
    @Expose
    private String date;

    public Ritase(int jumlahMobil, int dayaAngkut, double tpHarian, String date) {
        this.jumlahMobil = jumlahMobil;
        this.dayaAngkut = dayaAngkut;
        this.tpHarian = tpHarian;
        this.date = date;
    }

    public Ritase(int jumlahMobil, int dayaAngkut, double tpHarian, double ritase) {
        this.jumlahMobil = jumlahMobil;
        this.dayaAngkut = dayaAngkut;
        this.tpHarian = tpHarian;
        this.ritase = ritase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getJumlahMobil() {
        return jumlahMobil;
    }

    public void setJumlahMobil(int jumlahMobil) {
        this.jumlahMobil = jumlahMobil;
    }

    public int getDayaAngkut() {
        return dayaAngkut;
    }

    public void setDayaAngkut(int dayaAngkut) {
        this.dayaAngkut = dayaAngkut;
    }

    public double getTpHarian() {
        return tpHarian;
    }

    public void setTpHarian(double tpHarian) {
        this.tpHarian = tpHarian;
    }

    public double getRitase() {
        return ritase;
    }

    public void setRitase(double ritase) {
        this.ritase = ritase;
    }
}
