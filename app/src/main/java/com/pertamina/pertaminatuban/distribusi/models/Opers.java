package com.pertamina.pertaminatuban.distribusi.models;

public class Opers {
    private String date;
    private float jumlahKeluar;
    private String minJamKeluar;
    private String maxJamKeluar;
    private String jamOperasional;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getJumlahKeluar() {
        return jumlahKeluar;
    }

    public void setJumlahKeluar(float jumlahKeluar) {
        this.jumlahKeluar = jumlahKeluar;
    }

    public String getMinJamKeluar() {
        return minJamKeluar;
    }

    public void setMinJamKeluar(String minJamKeluar) {
        this.minJamKeluar = minJamKeluar;
    }

    public String getMaxJamKeluar() {
        return maxJamKeluar;
    }

    public void setMaxJamKeluar(String maxJamKeluar) {
        this.maxJamKeluar = maxJamKeluar;
    }

    public String getJamOperasional() {
        return jamOperasional;
    }

    public void setJamOperasional(String jamOperasional) {
        this.jamOperasional = jamOperasional;
    }
}
